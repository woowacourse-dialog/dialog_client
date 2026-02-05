package com.on.dialog.feature.login.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import com.on.dialog.feature.login.impl.model.LoginType
import com.on.dialog.feature.login.impl.viewmodel.LoginState
import io.github.aakira.napier.Napier
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSHTTPCookie
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKHTTPCookieStore
import platform.WebKit.WKNavigation
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
    val config = remember {
        WKWebViewConfiguration().apply {
            preferences.javaScriptEnabled = true
            websiteDataStore = WKWebsiteDataStore.defaultDataStore()
        }
    }

    // delegate를 강한 참조로 유지 (WKWebView의 navigationDelegate는 weak 참조)
    val navigationDelegate = remember {
        object : NSObject(), WKNavigationDelegateProtocol {
            override fun webView(webView: WKWebView, didFinishNavigation: WKNavigation?) {
                val url: String = webView.URL?.absoluteString ?: return

                handleLoginResult(
                    uiState = uiState,
                    url = url,
                    config = config,
                    onLoginSuccess = onLoginSuccess,
                    onLoginFailure = onLoginFailure,
                )
            }
        }
    }

    UIKitView(
        modifier = modifier.fillMaxSize(),
        factory = {
            val webView = WKWebView(
                frame = CGRectMake(x = 0.0, y = 0.0, width = 0.0, height = 0.0),
                configuration = config,
            )

            webView.navigationDelegate = navigationDelegate

            // 로그인 URL 로드
            val url = NSURL.URLWithString(loginType.loginUrl)
            if (url != null) {
                val request = NSURLRequest.requestWithURL(url)
                webView.loadRequest(request)
            } else {
                onLoginFailure()
            }

            webView
        },
    )
}

private fun handleLoginResult(
    uiState: LoginState,
    url: String,
    config: WKWebViewConfiguration,
    onLoginSuccess: (String, Boolean) -> Unit,
    onLoginFailure: () -> Unit,
) {
    // 로그인 성공 페이지 감지
    // 조건 : 로그인 페이지가 아니고, 다이얼로그 url로 돌아왔을 때
    if (!uiState.isLoginComplete && url.contains(other = BuildKonfig.BASE_URL)) {
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
                val isNewUser = isNewUser(url)
                onLoginSuccess(jsessionId, isNewUser)
            } else {
                Napier.d(tag = "LoginWebView", message = "⚠️ JSESSIONID not found in cookies")
                onLoginFailure()
            }
        }
    }
}
