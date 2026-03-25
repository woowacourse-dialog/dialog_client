package com.on.dialog.feature.login.impl.model

import kotlinx.cinterop.BetaInteropApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.AuthenticationServices.ASAuthorization
import platform.AuthenticationServices.ASAuthorizationAppleIDCredential
import platform.AuthenticationServices.ASAuthorizationAppleIDProvider
import platform.AuthenticationServices.ASAuthorizationController
import platform.AuthenticationServices.ASAuthorizationControllerDelegateProtocol
import platform.AuthenticationServices.ASAuthorizationControllerPresentationContextProvidingProtocol
import platform.AuthenticationServices.ASAuthorizationScopeEmail
import platform.AuthenticationServices.ASAuthorizationScopeFullName
import platform.AuthenticationServices.ASPresentationAnchor
import platform.Foundation.NSError
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import platform.darwin.NSObject
import kotlin.coroutines.resume

class AppleSignInHandler : OAuthSignInHandler {
    // ASAuthorizationController.delegate는 weak 참조 → GC 방지를 위해 strong 참조 유지
    private var delegate: NSObject? = null
    private var controller: ASAuthorizationController? = null

    @OptIn(BetaInteropApi::class)
    override suspend fun signIn(): Result<OAuthSignInResult> = suspendCancellableCoroutine { continuation ->
        // 이름·이메일 요청 (이름은 Apple이 최초 로그인 시에만 제공)
        val request = ASAuthorizationAppleIDProvider().createRequest().apply {
            requestedScopes = listOf(ASAuthorizationScopeFullName, ASAuthorizationScopeEmail)
        }

        val appleDelegate = object : NSObject(), ASAuthorizationControllerDelegateProtocol {
            override fun authorizationController(
                controller: ASAuthorizationController,
                didCompleteWithAuthorization: ASAuthorization,
            ) {
                val credential = didCompleteWithAuthorization.credential
                    as? ASAuthorizationAppleIDCredential
                    ?: run {
                        continuation.resume(Result.failure(IllegalStateException("Invalid credential type")))
                        return
                    }

                // identityToken: NSData → UTF-8 String 변환 후 서버로 전송
                val tokenData = credential.identityToken
                    ?: run {
                        continuation.resume(Result.failure(IllegalStateException("identityToken is null")))
                        return
                    }

                val identityToken =
                    NSString.create(data = tokenData, encoding = NSUTF8StringEncoding) as? String
                        ?: run {
                            continuation.resume(Result.failure(IllegalStateException("Failed to decode identityToken")))
                            return
                        }

                // fullName은 최초 로그인 시에만 제공, 이후 로그인에서는 null
                continuation.resume(
                    Result.success(
                        OAuthSignInResult(
                            identityToken = identityToken,
                            firstName = credential.fullName?.givenName,
                            lastName = credential.fullName?.familyName,
                        ),
                    ),
                )
            }

            override fun authorizationController(
                controller: ASAuthorizationController,
                didCompleteWithError: NSError,
            ) {
                continuation.resume(Result.failure(Exception(didCompleteWithError.localizedDescription)))
            }
        }
        delegate = appleDelegate

        val authController = ASAuthorizationController(authorizationRequests = listOf(request))
        authController.delegate = appleDelegate
        authController.presentationContextProvider = PresentationContextProvider()
        controller = authController

        // 코루틴 취소 시 참조 해제
        continuation.invokeOnCancellation {
            delegate = null
            controller = null
        }

        authController.performRequests()
    }
}

// Sign In with Apple UI를 표시할 UIWindow를 제공
private class PresentationContextProvider :
    NSObject(),
    ASAuthorizationControllerPresentationContextProvidingProtocol {
    override fun presentationAnchorForAuthorizationController(
        controller: ASAuthorizationController,
    ): ASPresentationAnchor {
        // 현재 활성화된 keyWindow 반환, 없으면 빈 UIWindow 폴백
        return UIApplication.sharedApplication.windows
            .filterIsInstance<UIWindow>()
            .firstOrNull { it.isKeyWindow() }
            ?: UIWindow()
    }
}
