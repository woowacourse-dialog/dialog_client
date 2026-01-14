package com.on.dialog.feature.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import io.github.aakira.napier.Napier
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSHTTPCookie
import platform.Foundation.NSHTTPCookieStorage
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun LoginWebViewScreen(
    loginType: LoginType,
    onLoginSuccess: () -> Unit,
    onLoginFailure: () -> Unit,
    viewModel: LoginViewModel,
) {
    var isLoginComplete: Boolean by remember { mutableStateOf(false) }

    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            val config = WKWebViewConfiguration()
            val webView = WKWebView(
                frame = CGRectMake(0.0, 0.0, 0.0, 0.0),
                configuration = config,
            )

            // NavigationDelegate 설정
            webView.navigationDelegate = object : NSObject(), WKNavigationDelegateProtocol {
                override fun webView(
                    webView: WKWebView,
                    didFinishNavigation: WKNavigation?,
                ) {
                    val url: String = webView.URL?.absoluteString ?: return

                    Napier.d("WebView URL: $url")

                    // 로그인 성공 페이지 감지
                    // 조건 : 로그인 페이지가 아니고, 다이얼로그 url로 돌아왔을 때
                    if (!isLoginComplete && !url.contains(loginType.keyword) && url.contains("woowa-dialog.com")) {
                        // 쿠키 추출
                        val cookieStorage = NSHTTPCookieStorage.sharedHTTPCookieStorage
                        val cookies = cookieStorage.cookies ?: emptyList<Any?>()

                        Napier.d("All Cookies: ${cookies.size}")

                        // JSESSIONID 추출
                        val jsessionId: String? = cookies
                            .filterIsInstance<NSHTTPCookie>()
                            .find { it.name == "JSESSIONID" }
                            ?.value

                        // JSESSIONID 추출 성공 시 콜백 함수로 반환
                        if (jsessionId != null) {
                            Napier.d("✅ JSESSIONID: $jsessionId")
                            isLoginComplete = true
                            viewModel.onIntent(LoginIntent.OnWebViewLoginSuccess(jsessionId))
                            onLoginSuccess()
                        } else {
                            Napier.w("⚠️ JSESSIONID not found in cookies")
                            viewModel.onIntent(LoginIntent.OnWebViewLoginFailure)
                            onLoginFailure()
                        }
                    }
                }
            }

            // 로그인 URL 로드
            val request = NSURLRequest.requestWithURL(NSURL.URLWithString(loginType.loginUrl)!!)
            webView.loadRequest(request)

            webView
        },
    )
}
