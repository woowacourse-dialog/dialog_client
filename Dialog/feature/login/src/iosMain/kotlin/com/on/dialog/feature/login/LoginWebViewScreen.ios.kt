package com.on.dialog.feature.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.WKWebsiteDataStore
import platform.WebKit.javaScriptEnabled
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun LoginWebViewScreen(
    loginType: LoginType,
    onLoginSuccess: () -> Unit,
    onLoginFailure: () -> Unit,
    onLoginCancel: () -> Unit,
    onSignUp: () -> Unit,
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

    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            val config = WKWebViewConfiguration().apply {
                // JavaScript 활성화 (Android의 javaScriptEnabled = true와 동일)
                preferences.javaScriptEnabled = true
                // WKWebView 전용 쿠키 저장소 사용
                websiteDataStore = WKWebsiteDataStore.defaultDataStore()
            }
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
                    val baseUrl: String = BuildKonfig.BASE_URL

                    Napier.d("🌐 BASE_URL: $baseUrl")
                    Napier.d("isLoginComplete: $isLoginComplete")

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
                    if (!isLoginComplete && url.contentEquals(baseUrl)) {
                        // WKWebView 전용 쿠키 저장소에서 쿠키 추출
                        val cookieStore = config.websiteDataStore.httpCookieStore
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
