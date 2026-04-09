package com.on.dialog.main.di

import com.on.dialog.main.navigation.SavedStateConfigurationProvider
import com.on.dialog.navigation.NavKeyProvider
import org.koin.dsl.module

val mainModule = module {
    single {
        SavedStateConfigurationProvider(
            providers = getAll<NavKeyProvider>(),
        )
    }
}
