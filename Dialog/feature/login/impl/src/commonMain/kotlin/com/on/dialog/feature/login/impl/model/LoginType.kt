package com.on.dialog.feature.login.impl.model

enum class LoginType(
    val loginUrl: String,
) {
    GITHUB(loginUrl = "api/oauth2/authorization/github"),
    GOOGLE(loginUrl = ""),
}
