package com.on.network.di

import com.on.dialog.core.network.BuildKonfig
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    // HttpClient 생성 시 CookiesStorage 주입
    single<HttpClient> {
        createHttpClient(cookiesStorage = get())
    }

    single<Ktorfit> {
        Ktorfit
            .Builder()
            .httpClient(get<HttpClient>())
            .baseUrl(BuildKonfig.BASE_URL)
            .build()
    }
}
