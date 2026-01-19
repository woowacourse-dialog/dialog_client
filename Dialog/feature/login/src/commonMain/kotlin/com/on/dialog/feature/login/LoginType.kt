package com.on.dialog.feature.login

enum class LoginType(
    val loginUrl: String,
) {
    GITHUB(loginUrl = BuildKonfig.GITHUB_OAUTH_URL),
    GOOGLE(loginUrl = ""),
}
