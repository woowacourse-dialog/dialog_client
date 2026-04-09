package com.on.dialog.feature.signup.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.AuthRepository
import com.on.dialog.domain.repository.SessionRepository
import com.on.dialog.domain.usecase.session.CheckLoginStatusUseCase
import com.on.dialog.model.common.Track
import com.on.dialog.ui.viewmodel.BaseViewModel
import dialog.feature.signup.impl.generated.resources.Res
import dialog.feature.signup.impl.generated.resources.save_user_id_fail
import dialog.feature.signup.impl.generated.resources.signup_failure
import dialog.feature.signup.impl.generated.resources.signup_success
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val sessionRepository: SessionRepository,
    private val checkLoginStatusUseCase: CheckLoginStatusUseCase,
) : BaseViewModel<SignUpIntent, SignUpState, SignUpEffect>(initialState = SignUpState()) {
    override fun onIntent(intent: SignUpIntent) {
        when (intent) {
            SignUpIntent.CancelSignUp -> cancelSignUp()
            is SignUpIntent.SelectTrack -> updateState { copy(selectedTrack = intent.track) }
            is SignUpIntent.ToggleNotification -> updateState { copy(isNotificationEnabled = intent.enabled) }
            is SignUpIntent.ValidateAndSignUp -> handleSignup(jsessionId = intent.jsessionId)
        }
    }

    private fun handleSignup(jsessionId: String) {
        val selectedTrack = currentState.selectedTrack ?: return
        viewModelScope.launch {
            if (jsessionId.isNotEmpty()) {
                saveSessionAndSignup(jsessionId = jsessionId, track = selectedTrack)
            } else {
                // Apple 로그인: Ktor HttpCookies가 JSESSIONID를 이미 저장했으므로 saveSession 불필요
                signup(
                    track = selectedTrack,
                    isNotificationEnabled = currentState.isNotificationEnabled,
                )
            }
        }
    }

    private suspend fun saveSessionAndSignup(jsessionId: String, track: Track) {
        sessionRepository
            .saveSession(jsessionId = jsessionId)
            .onSuccess {
                signup(
                    track = track,
                    isNotificationEnabled = currentState.isNotificationEnabled,
                )
            }.onFailure {
                emitEffect(
                    SignUpEffect.ShowSnackbar(
                        stringResource = Res.string.signup_failure,
                        state = SnackbarState.NEGATIVE,
                    ),
                )
            }
    }

    private suspend fun signup(track: Track, isNotificationEnabled: Boolean) {
        authRepository
            .signup(track = track, webPushNotification = isNotificationEnabled)
            .onSuccess { userId ->
                saveUserId(userId)
                checkLoginStatusUseCase()
            }.onFailure {
                sessionRepository.clearSession()
                sessionRepository.clearUserId()
                emitEffect(
                    SignUpEffect.ShowSnackbar(
                        stringResource = Res.string.signup_failure,
                        state = SnackbarState.NEGATIVE,
                    ),
                )
            }
    }

    private fun cancelSignUp() {
        viewModelScope.launch {
            sessionRepository.clearSession()
            sessionRepository.clearUserId()
            emitEffect(SignUpEffect.ExitSignUp)
        }
    }

    private suspend fun saveUserId(userId: Long) {
        sessionRepository
            .saveUserId(userId)
            .onSuccess {
                emitEffect(
                    SignUpEffect.ShowSnackbar(
                        stringResource = Res.string.signup_success,
                        state = SnackbarState.POSITIVE,
                        nonDismissable = true,
                    ),
                )
                emitEffect(SignUpEffect.NavigateHome)
            }.onFailure {
                authRepository.logout()
                sessionRepository.clearSession()
                sessionRepository.clearUserId()
                emitEffect(
                    SignUpEffect.ShowSnackbar(
                        stringResource = Res.string.save_user_id_fail,
                        state = SnackbarState.NEGATIVE,
                    ),
                )
                emitEffect(SignUpEffect.NavigateHome)
            }
    }
}
