package com.on.dialog.feature.login.impl.model

data class OAuthSignInResult(
    val identityToken: String,
    val firstName: String?,
    val lastName: String?,
)
