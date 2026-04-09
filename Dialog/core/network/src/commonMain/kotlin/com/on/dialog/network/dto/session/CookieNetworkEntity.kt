package com.on.dialog.network.dto.session

import io.ktor.http.Cookie

class CookieNetworkEntity(
    val name: String,
    val value: String,
    val domain: String,
    val path: String,
    val secure: Boolean = true,
    val httpOnly: Boolean = true,
) {
    fun toCookie(): Cookie = Cookie(
        name = name,
        value = value,
        domain = domain,
        path = path,
        secure = secure,
        httpOnly = httpOnly,
    )
}
