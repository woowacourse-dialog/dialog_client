package com.on.network.di

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        HttpClientFactory.create()
    }

    single<Ktorfit> {
        Ktorfit.Builder()
            .httpClient(get<HttpClient>())
            .baseUrl("")
            .build()
    }
}

