package com.on.dialog.feature.login.impl

enum class LoginType(
    val loginUrl: String,
) {
    GITHUB(loginUrl = BuildKonfig.GITHUB_OAUTH_URL),
    GOOGLE(loginUrl = ""),
}
