package com.on.dialog.model.auth

data class OAuthLoginResult(
    val isRegistered: Boolean,
    val userId: Long?,
    val nickname: String?,
)
