package com.on.dialog.feature.login

import androidx.lifecycle.viewModelScope
import com.on.dialog.domain.repository.SessionRepository
import com.on.dialog.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionRepository: SessionRepository,
) : BaseViewModel<LoginIntent, LoginState, LoginEffect>(
        initialState = LoginState(),
    ) {
    override fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginVia -> navigateToLogin(intent.loginType)
            is LoginIntent.LoginSuccess -> saveUserSession(intent.jsessionId)
            LoginIntent.LoginFailure -> notifyLoginError()
            LoginIntent.CancelLogin -> cancelLogin()
        }
    }

    private fun navigateToLogin(loginType: LoginType) {
        emitEffect(LoginEffect.OpenLoginWebView(loginType))
    }

    private fun saveUserSession(jsessionId: String) {
        updateState { copy(isLoading = true) }

        viewModelScope.launch {
            sessionRepository
                .saveSession(
                    requestUrl = BuildKonfig.BASE_URL,
                    jsessionId = jsessionId,
                ).onSuccess {
                    updateState { copy(isLoading = false) }
                }.onFailure { error ->
                    updateState { copy(isLoading = false) }
                    emitEffect(
                        LoginEffect.ShowError(
                            error.message ?: ERROR_MESSAGE_SESSION_SAVE_FAILED,
                        ),
                    )
                }
        }
    }

    private fun notifyLoginError() {
        updateState { copy(isLoading = false) }
        emitEffect(LoginEffect.ShowError(ERROR_MESSAGE_LOGIN_FAILED))
        emitEffect(LoginEffect.CloseLoginWebView)
    }

    private fun cancelLogin() {
        updateState { copy(isLoading = false) }
        emitEffect(LoginEffect.CloseLoginWebView)
    }

    companion object {
        private const val ERROR_MESSAGE_LOGIN_FAILED = "로그인에 실패했습니다."
        private const val ERROR_MESSAGE_SESSION_SAVE_FAILED = "JSESSIONID 저장에 실패했습니다."
    }
}
