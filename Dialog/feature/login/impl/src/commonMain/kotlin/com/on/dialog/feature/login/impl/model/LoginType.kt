package com.on.dialog.feature.login.impl.model

private const val LOGIN_BASE_URL = "api/oauth2/authorization/"

enum class LoginType(
    val loginUrl: String = "",
) {
    NONE,
    GITHUB(loginUrl = LOGIN_BASE_URL + "github"),
    APPLE,
}
