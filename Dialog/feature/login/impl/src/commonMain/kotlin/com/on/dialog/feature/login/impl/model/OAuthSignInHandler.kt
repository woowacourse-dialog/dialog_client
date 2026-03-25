package com.on.dialog.feature.login.impl.model

interface OAuthSignInHandler {
    suspend fun signIn(): Result<OAuthSignInResult>
}
