package com.on.dialog.network.di

import com.on.dialog.domain.repository.SessionRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.cookies.CookiesStorage

actual fun createHttpClient(cookiesStorage: CookiesStorage, sessionRepository: SessionRepository): HttpClient =
    HttpClient(engineFactory = Darwin) {
        installContentNegotiation()
        installLogging()
        installCookies(cookiesStorage = cookiesStorage)
        installUnauthorizedHandler(sessionRepository = sessionRepository)
    }
