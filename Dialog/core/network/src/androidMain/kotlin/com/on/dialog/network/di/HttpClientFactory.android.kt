package com.on.dialog.network.di

import com.on.dialog.domain.repository.SessionRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.cookies.CookiesStorage

actual fun createHttpClient(cookiesStorage: CookiesStorage, sessionRepository: SessionRepository): HttpClient =
    HttpClient(engineFactory = OkHttp) {
        installContentNegotiation()
        installLogging()
        installCookies(cookiesStorage)
        installUnauthorizedHandler(sessionRepository)
    }
