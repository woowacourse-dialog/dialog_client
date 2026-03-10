package com.on.dialog.feature.login.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.feature.login.impl.model.LoginType
import com.on.dialog.feature.login.impl.viewmodel.LoginEffect
import com.on.dialog.feature.login.impl.viewmodel.LoginIntent
import com.on.dialog.feature.login.impl.viewmodel.LoginState
import com.on.dialog.feature.login.impl.viewmodel.LoginViewModel
import com.on.dialog.ui.state.LocalAppLoginState
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel

/**
 * 지정된 [LoginType]으로 소셜 로그인을 수행하는 웹뷰 화면입니다.
 *
 * @param loginType 로그인 방식 (GitHub, Google 등)
 * @param onLoginSuccess 로그인 성공 시 호출되는 콜백
 * @param onLoginFailure 로그인 실패 시 호출되는 콜백
 * @param onLoginCancel 로그인 취소 시 호출되는 콜백
 * @param viewModel 로그인 로직을 관리하는 뷰모델
 */

@Composable
expect fun LoginWebView(
    uiState: LoginState,
    loginType: LoginType,
    onLoginSuccess: (jsessionId: String, isNewUser: Boolean) -> Unit,
    onLoginFailure: () -> Unit,
    onLoginCancel: () -> Unit,
    modifier: Modifier = Modifier,
)

@Composable
fun LoginWebViewScreen(
    loginType: LoginType,
    goBack: () -> Unit,
    navigateToSignUp: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val snackbarHostState = LocalSnackbarDelegate.current
    val appLoginState = LocalAppLoginState.current
    val uiState: LoginState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.OnLoginSuccess -> {
                    appLoginState.setLoggedIn(isLoggedIn = true)
                }

                LoginEffect.GoBack -> {
                    goBack()
                }

                LoginEffect.NavigateToSignUp -> {
                    navigateToSignUp()
                }

                is LoginEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = getString(effect.stringResource),
                        state = effect.state,
                    )
                }
            }
        }
    }

    LoginWebView(
        uiState = uiState,
        loginType = loginType,
        onLoginSuccess = { jsessionId, isNewUser ->
            viewModel.onIntent(
                intent = LoginIntent.LoginSuccess(jsessionId = jsessionId, isNewUser = isNewUser),
            )
        },
        onLoginFailure = {
            snackbarHostState.showSnackbar(
                state = SnackbarState.NEGATIVE,
                message = "로그인 실패 (JSESSION ID 없음)",
            )
            goBack()
        },
        onLoginCancel = goBack,
    )
}

internal fun isNewUser(url: String): Boolean = when (url.substringAfter(BuildKonfig.BASE_URL)) {
    "signup" -> true
    else -> false
}
