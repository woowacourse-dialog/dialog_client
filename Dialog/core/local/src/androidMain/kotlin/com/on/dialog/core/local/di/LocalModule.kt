package com.on.dialog.core.local.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.on.dialog.core.local.datasource.FcmTokenProvider
import com.on.dialog.core.local.datasourceimpl.AndroidFcmTokenProvider
import com.on.dialog.core.local.datasourceimpl.LocalCookieStorage
import com.on.dialog.core.local.datasourceimpl.LocalUserStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val localModule: Module = module {
    single<DataStore<Preferences>> {
        createDataStore(context = get())
    }
    single<LocalCookieStorage> {
        LocalCookieStorage(dataStore = get())
    }
    single<LocalUserStorage> {
        LocalUserStorage(dataStore = get())
    }
    single<FcmTokenProvider> {
        AndroidFcmTokenProvider()
    }
}
