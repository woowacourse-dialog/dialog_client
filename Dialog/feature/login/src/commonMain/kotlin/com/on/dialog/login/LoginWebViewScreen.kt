package com.on.dialog.login

import androidx.compose.runtime.Composable

internal const val DIALOG_URL = "https://woowa-dialog.com"

enum class LoginType(
    val keyword: String,
    val loginUrl: String,
) {
    GITHUB("github", "https://woowa-dialog.com/api/oauth2/authorization/github"),
    GOOGLE("google", ""),
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
    onLoginSuccess: (String) -> Unit,
    onLoginFailure: () -> Unit,
)
