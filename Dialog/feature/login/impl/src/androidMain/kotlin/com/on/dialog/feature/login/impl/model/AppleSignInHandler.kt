package com.on.dialog.feature.login.impl.model

// Apple Sign In은 Android 미지원 — 버튼은 iOS 전용으로 노출되므로 실제 호출되지 않음
class AppleSignInHandler : OAuthSignInHandler {
    override suspend fun signIn(): Result<OAuthSignInResult> =
        Result.failure(UnsupportedOperationException("Apple Sign In is not supported on Android"))
}
