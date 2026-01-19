package com.on.dialog.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

/**
 * 지정된 [LoginType]으로 소셜 로그인을 수행하는 웹뷰 화면입니다.
 *
 * @param loginType 로그인 방식 (GitHub, Google 등)
 * @param onLoginSuccess 로그인 성공 시 호출되는 콜백
 * @param onLoginFailure 로그인 실패 시 호출되는 콜백
 * @param onLoginCancel 로그인 취소 시 호출되는 콜백
 * @param onSignUp 회원가입이 필요한 경우 호출되는 콜백
 * @param viewModel 로그인 로직을 관리하는 뷰모델
 */
@Composable
expect fun LoginWebView(
    loginType: LoginType,
    onLoginSuccess: () -> Unit,
    onLoginFailure: () -> Unit,
    onLoginCancel: () -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
)
