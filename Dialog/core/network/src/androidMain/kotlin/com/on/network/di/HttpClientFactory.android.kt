package com.on.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.cookies.CookiesStorage

actual fun createHttpClient(cookiesStorage: CookiesStorage): HttpClient = HttpClient(OkHttp) {
    installContentNegotiation()
    installLogging()
    installCookies(cookiesStorage)
}
