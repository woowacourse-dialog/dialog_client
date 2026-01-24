package com.on.dialog.scrap.impl.di

import com.on.dialog.navigation.NavKeyProvider
import org.koin.dsl.bind
import org.koin.dsl.module

val scrapModule = module {
    single { ScrapNavKeyProvider() } bind NavKeyProvider::class
}
