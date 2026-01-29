package com.on.dialog.feature.login.impl

import androidx.lifecycle.viewModelScope
import com.on.dialog.domain.repository.SessionRepository
import com.on.dialog.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionRepository: SessionRepository,
) : BaseViewModel<LoginIntent, LoginState, LoginEffect>(initialState = LoginState()) {
    override fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginSuccess -> saveUserSession(
                jsessionId = intent.jsessionId,
                isNewUser = intent.isNewUser,
            )
        }
    }

    private fun saveUserSession(jsessionId: String, isNewUser: Boolean) {
        if (currentState.isLoginComplete) return
        updateState { copy(isLoading = true) }

        viewModelScope
            .launch {
                sessionRepository
                    .saveSession(jsessionId = jsessionId)
                    .onSuccess {
                        updateState { copy(isLoginComplete = true, isNewUser = isNewUser) }
                        emitEffect(if (isNewUser) LoginEffect.NavigateToSignUp else LoginEffect.GoBack)
                    }.onFailure { error ->
                        emitEffect(
                            effect = LoginEffect.ShowError(
                                message = error.message ?: ERROR_MESSAGE_SESSION_SAVE_FAILED,
                            ),
                        )
                    }
            }.invokeOnCompletion {
                updateState { copy(isLoading = false) }
            }
    }

    companion object {
        private const val ERROR_MESSAGE_SESSION_SAVE_FAILED = "JSESSIONID 저장에 실패했습니다."
    }
}
