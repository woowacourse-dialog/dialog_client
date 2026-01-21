package com.on.dialog.feature.main.impl.di

import com.on.navigation.NavKeyProvider
import org.koin.dsl.bind
import org.koin.dsl.module

val mainModule = module {
    single { MainNavKeyProvider() } bind NavKeyProvider::class
}
