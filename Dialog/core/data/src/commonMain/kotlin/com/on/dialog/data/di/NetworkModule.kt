package com.on.dialog.data.di

import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        HttpClientFactory.create()
    }
}
