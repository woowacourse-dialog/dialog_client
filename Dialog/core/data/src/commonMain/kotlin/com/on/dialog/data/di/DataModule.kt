package com.on.dialog.data.di

import com.on.dialog.data.cookie.CookieStoreAdapter
import com.on.dialog.network.datasource.CookieStore
import org.koin.dsl.module

val dataModule = module {
    single<com.on.dialog.network.datasource.CookieStore> {
        CookieStoreAdapter(localCookieStorage = get())
    }
}
