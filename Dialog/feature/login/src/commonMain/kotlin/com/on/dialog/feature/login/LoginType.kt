package com.on.dialog.feature.login

enum class LoginType(
    val loginUrl: String,
) {
    GITHUB(BuildKonfig.GITHUB_OAUTH_URL),
    GOOGLE(""),
}
