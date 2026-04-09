package com.on.dialog.data.di

import com.on.dialog.core.local.di.localModule
import com.on.dialog.data.cookie.CookieStoreAdapter
import com.on.dialog.network.datasource.CookieStore
import com.on.dialog.network.di.datasourceModule
import com.on.dialog.network.di.networkModule
import com.on.dialog.network.di.serviceModule
import org.koin.dsl.module

val dataModule = module {
    includes(
        networkModule,
        localModule,
        serviceModule,
        datasourceModule,
        repositoryModule,
    )

    single<CookieStore> {
        CookieStoreAdapter(localCookieStorage = get())
    }
}
