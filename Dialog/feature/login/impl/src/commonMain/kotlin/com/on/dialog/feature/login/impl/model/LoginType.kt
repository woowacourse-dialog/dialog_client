package com.on.dialog.feature.login.impl.model

private const val LOGIN_BASE_URL = "api/oauth2/authorization/"

enum class LoginType(
    val loginUrl: String,
) {
    GITHUB(loginUrl = LOGIN_BASE_URL + "github"),
    GOOGLE(loginUrl = ""),
}
