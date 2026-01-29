package com.on.dialog.feature.login.impl.model

import com.on.dialog.feature.login.impl.BuildKonfig

enum class LoginType(
    val loginUrl: String,
) {
    GITHUB(loginUrl = BuildKonfig.GITHUB_OAUTH_URL),
    GOOGLE(loginUrl = ""),
}
