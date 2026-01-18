package com.on.dialog.feature.login

import androidx.lifecycle.viewModelScope
import com.on.dialog.domain.repository.SessionRepository
import com.on.dialog.ui.viewmodel.BaseViewModel
import com.on.dialog.ui.viewmodel.UiEffect
import com.on.dialog.ui.viewmodel.UiIntent
import com.on.dialog.ui.viewmodel.UiState
import kotlinx.coroutines.launch

data class LoginState(
    val isLoading: Boolean = false,
) : UiState

/**
 * 로그인 화면에서 발생하는 사용자의 의도(Intent)를 정의하는 인터페이스입니다.
 *
 * @property LoginVia 특정 로그인 방식을 통한 로그인 프로세스를 시작합니다.
 * @property CancelLogin 로그인 과정을 취소합니다.
 * @property LoginSuccess 로그인 성공 시 세션 정보를 저장합니다.
 * @property LoginFailure 로그인 실패 상황을 처리합니다.
kotlin
 */
sealed interface LoginIntent : UiIntent {
    data class LoginVia(
        val loginType: LoginType,
    ) : LoginIntent

    data object CancelLogin : LoginIntent

    data class LoginSuccess(
        val jsessionId: String,
        val isNewUser: Boolean = false,
    ) : LoginIntent

    data object LoginFailure : LoginIntent
}

sealed interface LoginEffect : UiEffect {
    data class OpenLoginWebView(
        val loginType: LoginType,
    ) : LoginEffect

    data object CloseLoginWebView : LoginEffect

    data class ShowError(
        val message: String,
    ) : LoginEffect
}

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
