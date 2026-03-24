package com.on.dialog.feature.login.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.on.dialog.feature.login.impl.model.LoginType
import com.on.dialog.feature.login.impl.viewmodel.LoginState

/**
 * 지정된 [LoginType]으로 소셜 로그인을 수행하는 웹뷰 화면입니다.
 *
 * @param onLoginSuccess 로그인 성공 시 호출되는 콜백
 * @param onLoginFailure 로그인 실패 시 호출되는 콜백
 * @param onLoginCancel 로그인 취소 시 호출되는 콜백
 * @param viewModel 로그인 로직을 관리하는 뷰모델
 */

@Composable
expect fun LoginWebView(
    uiState: LoginState,
    onLoginSuccess: (jsessionId: String, isNewUser: Boolean) -> Unit,
    onLoginFailure: () -> Unit,
    onLoginCancel: () -> Unit,
    modifier: Modifier = Modifier,
)

@Composable
fun LoginWebViewScreen(
    uiState: LoginState,
    goBack: () -> Unit,
    onLoginSuccess: (String, Boolean) -> Unit,
    onLoginFailure: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LoginWebView(
        uiState = uiState,
        onLoginSuccess = onLoginSuccess,
        onLoginFailure = onLoginFailure,
        onLoginCancel = goBack,
        modifier = modifier,
    )
}

internal fun isNewUser(url: String): Boolean = when (url.substringAfter(BuildKonfig.BASE_URL)) {
    "signup" -> true
    else -> false
}
