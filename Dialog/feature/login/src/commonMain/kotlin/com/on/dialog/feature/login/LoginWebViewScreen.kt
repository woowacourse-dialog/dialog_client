package com.on.dialog.feature.login

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

enum class LoginType(
    val loginUrl: String,
) {
    GITHUB(BuildKonfig.GITHUB_OAUTH_URL),
    GOOGLE(""),
}

/**
 * 지정된 [LoginType]으로 소셜 로그인을 수행하는 웹뷰 화면입니다.
 *
 * @param loginType 로그인 방식 (GitHub, Google 등)
 * @param onLoginSuccess 로그인 성공 시 호출되며, 세션 ID(JSESSIONID)를 전달합니다.
 * @param onLoginFailure 로그인 실패 또는 취소 시 호출됩니다.
 */
@Composable
expect fun LoginWebViewScreen(
    loginType: LoginType,
    onLoginSuccess: () -> Unit,
    onLoginFailure: () -> Unit,
    onLoginCancel: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
)
