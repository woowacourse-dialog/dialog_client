package com.on.dialog.feature.signup.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.AuthRepository
import com.on.dialog.model.common.Track
import com.on.dialog.ui.viewmodel.BaseViewModel
import com.on.dialog.ui.viewmodel.UiEffect
import com.on.dialog.ui.viewmodel.UiIntent
import com.on.dialog.ui.viewmodel.UiState
import kotlinx.coroutines.launch

data object SignUpState : UiState

interface SignUpIntent : UiIntent {
    data class Signup(
        val track: Track,
        val isNotificationEnabled: Boolean,
    ) : SignUpIntent
}

interface SignUpEffect : UiEffect {
    data object NavigateHome : SignUpEffect

    data class ShowSnackbar(
        val message: String,
        val state: SnackbarState,
    ) : SignUpEffect
}

class SignUpViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel<SignUpIntent, SignUpState, SignUpEffect>(initialState = SignUpState) {
    override fun onIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.Signup -> signup(
                track = intent.track,
                isNotificationEnabled = intent.isNotificationEnabled,
            )
        }
    }

    fun signup(track: Track, isNotificationEnabled: Boolean) {
        viewModelScope
            .launch {
                authRepository
                    .signup(track = track, webPushNotification = isNotificationEnabled)
                    .onSuccess {
                        emitEffect(
                            effect = SignUpEffect.ShowSnackbar(
                                message = "🎉 회원가입에 성공했습니다.",
                                state = SnackbarState.POSITIVE,
                            ),
                        )
                        emitEffect(effect = SignUpEffect.NavigateHome)
                    }.onFailure {
                        emitEffect(
                            effect = SignUpEffect.ShowSnackbar(
                                message = "회원가입에 실패했습니다.",
                                state = SnackbarState.NEGATIVE,
                            ),
                        )
                    }
            }.invokeOnCompletion { emitEffect(SignUpEffect.NavigateHome) }
    }
}
