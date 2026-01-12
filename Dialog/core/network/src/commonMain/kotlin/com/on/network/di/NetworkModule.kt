package com.on.network.di

import com.on.dialog.core.network.BuildKonfig
import com.on.network.storage.PersistentCookieStorage
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.CookiesStorage
import org.koin.dsl.module

val networkModule = module {
    // CookiesStorage 싱글톤
    single<CookiesStorage> {
        PersistentCookieStorage(get())
    }

    // HttpClient 생성 시 CookiesStorage 주입
    single<HttpClient> {
        createHttpClient(get())
    }

    single<Ktorfit> {
        Ktorfit
            .Builder()
            .httpClient(get<HttpClient>())
            .baseUrl(BuildKonfig.BASE_URL)
            .build()
    }
}
