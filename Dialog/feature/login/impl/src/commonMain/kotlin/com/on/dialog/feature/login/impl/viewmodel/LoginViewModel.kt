package com.on.dialog.feature.login.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.SessionRepository
import com.on.dialog.ui.viewmodel.BaseViewModel
import dialog.feature.login.impl.generated.resources.Res
import dialog.feature.login.impl.generated.resources.error_save_session
import io.github.aakira.napier.Napier
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
                    .onSuccess { handleSaveUserSessionSuccess(isNewUser = isNewUser) }
                    .onFailure { handleSaveUserSessionFailure() }
            }.invokeOnCompletion { updateState { LoginState() } }
    }

    private fun handleSaveUserSessionSuccess(isNewUser: Boolean) {
        updateState { copy(isLoginComplete = true, isNewUser = isNewUser) }
        emitEffect(if (isNewUser) LoginEffect.NavigateToSignUp else LoginEffect.GoBack)
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

    companion object {
        private const val ERROR_MESSAGE_SESSION_SAVE_FAILED = "JSESSIONID 저장에 실패했습니다."
    }
}
