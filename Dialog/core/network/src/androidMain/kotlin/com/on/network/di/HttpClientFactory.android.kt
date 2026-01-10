package com.on.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual fun createHttpClient(): HttpClient = HttpClient(OkHttp) {
    installContentNegotiation()
    installLogging()
}
