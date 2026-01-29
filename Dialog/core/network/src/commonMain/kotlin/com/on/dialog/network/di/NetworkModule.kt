package com.on.dialog.network.di

import com.on.dialog.core.network.BuildKonfig
import com.on.dialog.network.common.DataResponseConverterFactory
import com.on.dialog.network.datasourceimpl.KtorCookiesStorage
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.CookiesStorage
import org.koin.dsl.module

val networkModule = module {
    single<CookiesStorage> {
        KtorCookiesStorage(cookieStore = get())
    }

    single<HttpClient> {
        createHttpClient(cookiesStorage = get())
    }

    single<Ktorfit> {
        Ktorfit
            .Builder()
            .converterFactories(DataResponseConverterFactory())
            .httpClient(client = get<HttpClient>())
            .baseUrl(BuildKonfig.BASE_URL)
            .build()
    }
}
