package com.on.dialog.feature.login.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.core.common.error.NetworkError
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.AuthRepository
import com.on.dialog.domain.repository.SessionRepository
import com.on.dialog.domain.repository.UserRepository
import com.on.dialog.domain.usecase.session.CheckLoginStatusUseCase
import com.on.dialog.feature.login.impl.model.LoginType
import com.on.dialog.feature.login.impl.model.OAuthSignInHandler
import com.on.dialog.feature.login.impl.model.OAuthSignInResult
import com.on.dialog.ui.mapper.UNKNOWN_DIALOG_ERROR
import com.on.dialog.ui.mapper.toDialogErrorStringResource
import com.on.dialog.ui.viewmodel.BaseViewModel
import dialog.feature.login.impl.generated.resources.Res
import dialog.feature.login.impl.generated.resources.error_apple_login
import dialog.feature.login.impl.generated.resources.error_save_session
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

class LoginViewModel(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val appleSignInHandler: OAuthSignInHandler,
    private val checkLoginStatusUseCase: CheckLoginStatusUseCase,
) : BaseViewModel<LoginIntent, LoginState, LoginEffect>(initialState = LoginState()) {
    override fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginSuccess -> handleLoginSuccess(
                jsessionId = intent.jsessionId,
                isNewUser = intent.isNewUser,
            )

            is LoginIntent.LoginFailure -> showSnackbar(
                message = intent.loginError.message,
                state = SnackbarState.NEGATIVE,
            )

            is LoginIntent.SelectLoginType -> handleLoginType(intent.loginType)
        }
    }

    private fun handleLoginSuccess(jsessionId: String, isNewUser: Boolean) {
        viewModelScope
            .launch {
                if (isNewUser) {
                    updateState { copy(isLoginComplete = true, isNewUser = true) }
                    emitEffect(LoginEffect.NavigateToSignUp(jsessionId = jsessionId))
                    return@launch
                }
                saveUserSession(jsessionId = jsessionId)
            }.invokeOnCompletion { updateState { LoginState() } }
    }

    private suspend fun saveUserSession(jsessionId: String) {
        if (currentState.isLoginComplete) return
        updateState { copy(isLoading = true) }

        sessionRepository
            .saveSession(jsessionId = jsessionId)
            .onSuccess { saveUserId() }
            .onFailure { handleSaveUserSessionFailure() }
    }

    private suspend fun saveUserId() {
        userRepository
            .getMyUserInfo()
            .onSuccess { userInfo ->
                sessionRepository
                    .saveUserId(userId = userInfo.id)
                    .onSuccess { handleSaveUserSessionSuccess() }
                    .onFailure { handleSaveUserSessionFailure() }
            }.onFailure { handleSaveUserSessionFailure() }
    }

    private suspend fun handleSaveUserSessionSuccess() {
        checkLoginStatusUseCase()
        updateState { copy(isLoginComplete = true, isNewUser = false) }
        emitEffect(LoginEffect.GoBack)
    }

    private fun handleSaveUserSessionFailure() {
        Napier.e(ERROR_MESSAGE_SESSION_SAVE_FAILED)
        emitEffect(
            effect = LoginEffect.ShowSnackbar(
                stringResource = Res.string.error_save_session,
                state = SnackbarState.NEGATIVE,
            ),
        )
    }

    private fun handleLoginType(loginType: LoginType) {
        when (loginType) {
            LoginType.GITHUB -> updateState { copy(loginType = loginType) }
            LoginType.APPLE -> handleAppleLogin()
            LoginType.NONE -> updateState { copy(loginType = loginType) }
        }
    }

    private fun handleAppleLogin() {
        viewModelScope
            .launch {
                updateState { copy(isLoading = true) }
                appleSignInHandler
                    .signIn()
                    .onSuccess { result: OAuthSignInResult -> handleAppleSignInHandlerSuccess(result) }
                    .onFailure {
                        emitEffect(
                            effect = LoginEffect.ShowSnackbar(
                                stringResource = Res.string.error_apple_login,
                                state = SnackbarState.NEGATIVE,
                            ),
                        )
                    }
            }.invokeOnCompletion { updateState { copy(isLoading = false) } }
    }

    private suspend fun handleAppleSignInHandlerSuccess(signInResult: OAuthSignInResult) {
        authRepository
            .postAppleLogin(
                identityToken = signInResult.identityToken,
                firstName = signInResult.firstName,
                lastName = signInResult.lastName,
            ).onSuccess { loginResult ->
                if (loginResult.isRegistered) {
                    saveUserId()
                } else {
                    updateState { copy(isLoginComplete = true, isNewUser = true) }
                    // 애플 로그인 시 POST 응답에서 setCookie로 JSESSIONID가 저장
                    emitEffect(LoginEffect.NavigateToSignUp(jsessionId = ""))
                }
            }.onFailure(::showErrorSnackbar)
    }

    private fun showSnackbar(
        message: StringResource,
        state: SnackbarState,
    ) {
        emitEffect(LoginEffect.ShowSnackbar(stringResource = message, state = state))
    }

    private fun showErrorSnackbar(throwable: Throwable) {
        Napier.e(message = throwable.message.orEmpty(), throwable = throwable)

        val message = when (throwable) {
            is NetworkError -> throwable.errorCode.toDialogErrorStringResource()
            else -> UNKNOWN_DIALOG_ERROR
        }
        showSnackbar(message = message, state = SnackbarState.NEGATIVE)
    }

    companion object {
        private const val ERROR_MESSAGE_SESSION_SAVE_FAILED = "JSESSIONID 저장에 실패했습니다."
    }
}
