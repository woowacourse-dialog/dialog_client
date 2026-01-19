package com.on.dialog.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import io.github.aakira.napier.Napier
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSHTTPCookie
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKHTTPCookieStore
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationAction
import platform.WebKit.WKNavigationActionPolicy
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.WKWebsiteDataStore
import platform.WebKit.javaScriptEnabled
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun LoginWebView(
    uiState: LoginState,
    loginType: LoginType,
    onLoginSuccess: (jsessionId: String, isNewUser: Boolean) -> Unit,
    onLoginFailure: () -> Unit,
    onLoginCancel: () -> Unit,
    modifier: Modifier,
) {
    UIKitView(
        modifier = modifier,
        factory = {
            val config = WKWebViewConfiguration().apply {
                preferences.javaScriptEnabled = false
                // WKWebView 전용 쿠키 저장소 사용
                websiteDataStore = WKWebsiteDataStore.defaultDataStore()
            }
            val webView = WKWebView(
                frame = CGRectMake(x = 0.0, y = 0.0, width = 0.0, height = 0.0),
                configuration = config,
            )

            // NavigationDelegate 설정
            webView.navigationDelegate = object : NSObject(), WKNavigationDelegateProtocol {
                var isNewUser: Boolean = true

                // Android의 shouldOverrideUrlLoading과 동일한 역할
                override fun webView(
                    webView: WKWebView,
                    decidePolicyForNavigationAction: WKNavigationAction,
                    decisionHandler: (WKNavigationActionPolicy) -> Unit,
                ) {
                    val url: String? = decidePolicyForNavigationAction.request.URL?.absoluteString

                    Napier.d("$url")

                    // scope 파라미터에서 신규/기존 유저 구분
                    // scope=read:user → 기존 유저
                    // scope=read:temp_user → 신규 회원가입
                    url?.contains(other = "read:user")?.let { isNewUser = false }

                    decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyAllow)
                }

                // Android의 onPageFinished와 동일한 역할
                override fun webView(
                    webView: WKWebView,
                    didFinishNavigation: WKNavigation?,
                ) {
                    val url: String = webView.URL?.absoluteString ?: return

                    Napier.d("WebView URL: $url")
                    Napier.d("isNewUser: $isNewUser")

                    handleLoginResult(
                        isNewUser = isNewUser,
                        uiState = uiState,
                        url = url,
                        config = config,
                        onLoginSuccess = onLoginSuccess,
                        onLoginFailure = onLoginFailure,
                    )
                }
            }

            // 로그인 URL 로드
            val url = NSURL.URLWithString(loginType.loginUrl)
            if (url != null) {
                val request = NSURLRequest.requestWithURL(url)
                webView.loadRequest(request)
            } else {
                Napier.e("Invalid login URL: ${loginType.loginUrl}")
                onLoginFailure()
            }

            webView
        },
    )
}

private fun handleLoginResult(
    isNewUser: Boolean,
    uiState: LoginState,
    url: String,
    config: WKWebViewConfiguration,
    onLoginSuccess: (String, Boolean) -> Unit,
    onLoginFailure: () -> Unit,
) {
    // 로그인 성공 페이지 감지
    // 조건 : 로그인 페이지가 아니고, 다이얼로그 url로 돌아왔을 때
    if (!uiState.isLoginComplete && url.contentEquals(other = BuildKonfig.BASE_URL)) {
        // WKWebView 전용 쿠키 저장소에서 쿠키 추출
        val cookieStore: WKHTTPCookieStore = config.websiteDataStore.httpCookieStore
        cookieStore.getAllCookies { cookies ->
            val cookieList = cookies ?: emptyList<Any?>()

            // JSESSIONID 추출
            val jsessionId: String? = cookieList
                .filterIsInstance<NSHTTPCookie>()
                .find { it.name == "JSESSIONID" }
                ?.value

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
}
