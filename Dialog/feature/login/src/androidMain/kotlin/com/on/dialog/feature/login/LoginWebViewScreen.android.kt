package com.on.dialog.feature.login

import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.github.aakira.napier.Napier

@Composable
actual fun LoginWebView(
    loginType: LoginType,
    onLoginSuccess: () -> Unit,
    onLoginFailure: () -> Unit,
    onLoginCancel: () -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier,
    viewModel: LoginViewModel,
) {
    var isLoginComplete: Boolean by remember { mutableStateOf(false) }
    var isNewUser: Boolean by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            if (effect is LoginEffect.CloseLoginWebView) {
                onLoginCancel()
                return@collect
            }
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            val webView = WebView(context)

            // Settings 설정
            webView.settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
            }

            // Cookie 설정
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.setAcceptThirdPartyCookies(webView, true)

            // WebViewClient 설정
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    val url: String = url ?: return

                    Napier.d("WebView URL: $url")

                    // scope 파라미터에서 신규/기존 유저 구분
                    // scope=read:user → 기존 유저
                    // scope=read:temp_user → 신규 회원가입
                    // %3a == 인코딩된 경우의 콜론
                    when {
                        url.contains("scope=read%3Atemp_user") ||
                            url.contains("scope=read:temp_user") -> {
                            isNewUser = true
                        }

                        url.contains("scope=read%3Auser") ||
                            url.contains("scope=read:user") -> {
                            isNewUser = false
                        }
                    }
                    Napier.d("isNewUser: $isNewUser")

                    // 로그인 성공 페이지 감지
                    // 조건 : 로그인 페이지가 아니고, 다이얼로그 url로 돌아왔을 때
                    if (!isLoginComplete && url.contentEquals(BuildKonfig.BASE_URL)) {
                        val cookies = cookieManager.getCookie(BuildKonfig.BASE_URL)
                        Napier.d("All Cookies: $cookies")

                        // JSESSIONID 추출
                        val jsessionId: String? = cookies
                            ?.split(";")
                            ?.map { it.trim() }
                            ?.find { it.startsWith("JSESSIONID=") }
                            ?.substringAfter("JSESSIONID=")

                        // JSESSIONID 추출 성공 시 콜백 함수로 반환
                        if (jsessionId != null) {
                            Napier.d("✅ JSESSIONID: $jsessionId, isNewUser: $isNewUser")
                            isLoginComplete = true
                            viewModel.onIntent(LoginIntent.LoginSuccess(jsessionId, isNewUser))

                            // 신규 유저면 회원가입, 기존 유저면 로그인
                            when (isNewUser) {
                                true -> onSignUp()
                                false -> onLoginSuccess()
                            }
                        } else {
                            Napier.w("⚠️ JSESSIONID not found in cookies")
                            viewModel.onIntent(LoginIntent.LoginFailure)
                            onLoginFailure()
                        }
                    }
                }
            }
            // 로그인 URL 로드
            webView.loadUrl(loginType.loginUrl)

            webView
        },
    )
}
