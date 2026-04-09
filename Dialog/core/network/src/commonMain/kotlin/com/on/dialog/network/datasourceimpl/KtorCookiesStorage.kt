package com.on.dialog.network.datasourceimpl

import com.on.dialog.network.datasource.CookieStore
import com.on.dialog.network.dto.session.CookieNetworkEntity
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.Url

class KtorCookiesStorage(
    private val cookieStore: CookieStore,
) : CookiesStorage {
    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        cookieStore.save(
            CookieNetworkEntity(
                name = cookie.name,
                value = cookie.value,
                domain = requestUrl.host,
                path = cookie.path ?: "/",
                secure = cookie.secure,
                httpOnly = cookie.httpOnly,
            ),
        )
    }

    override suspend fun get(requestUrl: Url): List<Cookie> =
        cookieStore
            .loadAll(requestHost = requestUrl.host, requestPath = requestUrl.encodedPath)
            .map { it.toCookie() }

    override fun close() = Unit
}
