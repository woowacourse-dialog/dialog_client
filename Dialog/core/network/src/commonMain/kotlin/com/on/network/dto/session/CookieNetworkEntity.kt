package com.on.network.dto.session

import io.ktor.http.Cookie
import io.ktor.util.date.GMTDate

class CookieNetworkEntity(
    val name: String,
    val value: String,
    val domain: String,
    val path: String,
    val expires: Long? = null,
    val secure: Boolean = true,
    val httpOnly: Boolean = true,
) {
    fun toCookie(): Cookie = Cookie(
        name = name,
        value = value,
        domain = domain,
        path = path,
        expires = expires?.let { GMTDate(timestamp = it) },
        secure = secure,
        httpOnly = httpOnly,
    )
}
