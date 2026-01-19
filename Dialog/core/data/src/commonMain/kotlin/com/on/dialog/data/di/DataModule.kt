package com.on.dialog.data.di

import com.on.dialog.data.cookie.CookieStoreAdapter
import com.on.network.datasource.CookieStore
import org.koin.dsl.module

val dataModule = module {
    single<CookieStore> {
        CookieStoreAdapter(localCookieStorage = get())
    }
}
