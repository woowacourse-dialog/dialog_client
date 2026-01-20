package com.on.dialog.core.local.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.on.dialog.core.local.datasourceimpl.LocalCookieStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val localModule: Module = module {
    single<DataStore<Preferences>> {
        createDataStore()
    }
    single<LocalCookieStorage> {
        LocalCookieStorage(dataStore = get())
    }
}
