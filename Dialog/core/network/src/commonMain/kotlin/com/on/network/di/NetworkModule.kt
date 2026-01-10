package com.on.network.di

import com.on.dialog.core.network.BuildKonfig
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        createHttpClient()
    }

    single<Ktorfit> {
        Ktorfit
            .Builder()
            .httpClient(get<HttpClient>())
            .baseUrl(BuildKonfig.BASE_URL)
            .build()
    }
}
