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
            is SignUpIntent.SelectTrack -> updateState { copy(selectedTrack = intent.track) }
            is SignUpIntent.ToggleNotification -> updateState { copy(isNotificationEnabled = intent.enabled) }
            SignUpIntent.ValidateAndSignUp -> handleSignup()
        }
    }

    private fun handleSignup() {
        val selectedTrack = currentState.selectedTrack ?: return
        signup(
            track = selectedTrack,
            isNotificationEnabled = currentState.isNotificationEnabled,
        )
    }

    private fun signup(track: Track, isNotificationEnabled: Boolean) {
        viewModelScope.launch {
            authRepository
                .signup(track = track, webPushNotification = isNotificationEnabled)
                .onSuccess { userId ->
                    saveUserId(userId)
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
