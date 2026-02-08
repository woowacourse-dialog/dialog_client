package com.on.dialog.feature.signup.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.AuthRepository
import com.on.dialog.model.common.Track
import com.on.dialog.ui.viewmodel.BaseViewModel
import dialog.feature.signup.impl.generated.resources.Res
import dialog.feature.signup.impl.generated.resources.signup_failure
import dialog.feature.signup.impl.generated.resources.signup_success
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel<SignUpIntent, SignUpState, SignUpEffect>(initialState = SignUpState()) {
    override fun onIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.SelectTrack ->
                updateState { copy(selectedTrackIndex = intent.index, isTrackSelected = true) }

            is SignUpIntent.ToggleNotification ->
                updateState { copy(isNotificationEnabled = intent.enabled) }

            SignUpIntent.ValidateAndSignUp -> handleSignup()
        }
    }

    private fun handleSignup() {
        val selectedIndex = currentState.selectedTrackIndex
        if (currentState.isTrackSelected != true || selectedIndex == null) {
            updateState { copy(isTrackSelected = false) }
            return
        }
        signup(
            track = Track.entries.filter { it != Track.COMMON }[selectedIndex],
            isNotificationEnabled = currentState.isNotificationEnabled,
        )
    }

    private fun signup(track: Track, isNotificationEnabled: Boolean) {
        viewModelScope
            .launch {
                authRepository
                    .signup(track = track, webPushNotification = isNotificationEnabled)
                    .onSuccess {
                        emitEffect(
                            effect = SignUpEffect.ShowSnackbar(
                                stringResource = Res.string.signup_success,
                                state = SnackbarState.POSITIVE,
                            ),
                        )
                    }.onFailure {
                        emitEffect(
                            effect = SignUpEffect.ShowSnackbar(
                                stringResource = Res.string.signup_failure,
                                state = SnackbarState.NEGATIVE,
                            ),
                        )
                    }
            }.invokeOnCompletion { emitEffect(SignUpEffect.NavigateHome) }
    }
}
