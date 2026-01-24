package com.on.dialog.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.on.dialog.feature.login.impl.BuildKonfig
import io.github.aakira.napier.Napier
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
    val uiState: LoginState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.GoBack -> goBack()
                LoginEffect.NavigateToSignUp -> navigateToSignUp()
                is LoginEffect.ShowError -> Unit
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
            // showSnackbar
            goBack()
        },
        onLoginCancel = {
            // showSnackbar
            Napier.d("onLoginCancel")
            goBack()
        },
    )
}

internal fun isNewUser(url: String): Boolean = when (url.substringAfter(BuildKonfig.BASE_URL)) {
    "signup" -> true
    else -> false
}
