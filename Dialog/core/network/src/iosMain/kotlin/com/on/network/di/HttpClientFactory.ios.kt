package com.on.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.cookies.CookiesStorage

actual fun createHttpClient(cookiesStorage: CookiesStorage): HttpClient =
    HttpClient(engineFactory = Darwin) {
        installContentNegotiation()
        installLogging()
        installCookies(cookiesStorage = cookiesStorage)
    }
