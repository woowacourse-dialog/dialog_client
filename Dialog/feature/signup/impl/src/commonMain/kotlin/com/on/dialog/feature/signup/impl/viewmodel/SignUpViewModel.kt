package com.on.dialog.feature.signup.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.AuthRepository
import com.on.dialog.domain.repository.SessionRepository
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
            sessionRepository
                .saveSession(jsessionId = jsessionId)
                .onSuccess {
                    signup(
                        track = selectedTrack,
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
    }

    private fun signup(track: Track, isNotificationEnabled: Boolean) {
        viewModelScope.launch {
            authRepository
                .signup(track = track, webPushNotification = isNotificationEnabled)
                .onSuccess { userId ->
                    saveUserId(userId)
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
