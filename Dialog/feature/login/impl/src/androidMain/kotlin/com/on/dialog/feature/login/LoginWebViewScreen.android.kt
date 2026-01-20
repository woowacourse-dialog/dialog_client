package com.on.dialog.feature.login

import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.on.dialog.feature.login.impl.BuildKonfig
import io.github.aakira.napier.Napier

@Composable
actual fun LoginWebView(
    uiState: LoginState,
    loginType: LoginType,
    onLoginSuccess: (jsessionId: String, isNewUser: Boolean) -> Unit,
    onLoginFailure: () -> Unit,
    onLoginCancel: () -> Unit,
    modifier: Modifier,
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
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
                var isNewUser: Boolean = true

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?,
                ): Boolean {
                    // scope 파라미터에서 신규/기존 유저 구분
                    // scope=read:user → 기존 유저
                    // scope=read:temp_user → 신규 회원가입
                    val scopeQueryParam: String? = request?.url?.getQueryParameter("scope")
                    Napier.d(tag = "Redirect", message = "${request?.url}")
                    scopeQueryParam?.contains("read:user")?.let { isNewUser = false }

                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    val url: String = url ?: return

                    Napier.d("WebView URL: $url")
                    Napier.d("isNewUser: $isNewUser")

                    handleLoginResult(
                        isNewUser = isNewUser,
                        uiState = uiState,
                        url = url,
                        cookieManager = cookieManager,
                        onLoginSuccess = onLoginSuccess,
                        onLoginFailure = onLoginFailure,
                    )
                }
            }
            // 로그인 URL 로드
            webView.loadUrl(loginType.loginUrl)

            webView
        },
    )
}

private fun handleLoginResult(
    isNewUser: Boolean,
    uiState: LoginState,
    url: String,
    cookieManager: CookieManager,
    onLoginSuccess: (String, Boolean) -> Unit,
    onLoginFailure: () -> Unit,
) {
    Napier.d("isNewUser: $isNewUser")

    // 로그인 성공 페이지 감지
    // 조건 : 로그인 페이지가 아니고, 다이얼로그 url로 돌아왔을 때
    if (!uiState.isLoginComplete && url.contentEquals(charSequence = BuildKonfig.BASE_URL)) {
        val cookies = cookieManager.getCookie(BuildKonfig.BASE_URL)
        Napier.d("All Cookies: $cookies")

        // JSESSIONID 추출
        val jsessionId: String? = cookies
            ?.split(";")
            ?.map { it.trim() }
            ?.find { it.startsWith(prefix = "JSESSIONID=") }
            ?.substringAfter(delimiter = "JSESSIONID=")

        // JSESSIONID 추출 성공 시 콜백 함수로 반환
        if (jsessionId != null) {
            Napier.d("✅ JSESSIONID: $jsessionId, isNewUser: $isNewUser")
            onLoginSuccess(jsessionId, isNewUser)
        } else {
            Napier.w("⚠️ JSESSIONID not found in cookies")
            onLoginFailure()
        }
    }
}
