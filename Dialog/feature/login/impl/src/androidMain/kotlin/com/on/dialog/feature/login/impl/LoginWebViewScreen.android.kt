package com.on.dialog.feature.login.impl

import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.on.dialog.feature.login.impl.model.LoginType
import com.on.dialog.feature.login.impl.viewmodel.LoginState
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
    var isLoginHandled: Boolean = false

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
                override fun onPageFinished(view: WebView?, url: String?) {
                    if (isLoginHandled) return
                    super.onPageFinished(view, url)
                    val url: String = url ?: return

                    handleLoginResult(
                        uiState = uiState,
                        url = url,
                        cookieManager = cookieManager,
                        onLoginSuccess = onLoginSuccess,
                        onLoginFailure = onLoginFailure,
                        onHandle = { isLoginHandled = true },
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
    uiState: LoginState,
    url: String,
    cookieManager: CookieManager,
    onLoginSuccess: (String, Boolean) -> Unit,
    onLoginFailure: () -> Unit,
    onHandle: () -> Unit,
) {
    onHandle()
    // 로그인 성공 페이지 감지
    // 조건 : 로그인 페이지가 아니고, 다이얼로그 url로 돌아왔을 때
    if (!uiState.isLoginComplete && url.startsWith(prefix = BuildKonfig.BASE_URL)) {
        val cookies = cookieManager.getCookie(BuildKonfig.BASE_URL)

        // JSESSIONID 추출
        val jsessionId: String? = cookies
            ?.split(";")
            ?.map { it.trim() }
            ?.find { it.startsWith(prefix = "JSESSIONID=") }
            ?.substringAfter(delimiter = "JSESSIONID=")

        // JSESSIONID 추출 성공 시 콜백 함수로 반환
        if (jsessionId != null) {
            val isNewUser = isNewUser(url)
            onLoginSuccess(jsessionId, isNewUser)
        } else {
            Napier.w(tag = "LoginWebView", message = "⚠️ JSESSIONID not found in cookies")
            onLoginFailure()
        }
    }
}
