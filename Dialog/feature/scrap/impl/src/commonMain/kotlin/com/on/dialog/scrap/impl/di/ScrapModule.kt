package com.on.dialog.scrap.impl.di

import com.on.dialog.feature.scrap.api.event.ScrapEventBus
import com.on.dialog.navigation.NavKeyProvider
import com.on.dialog.scrap.impl.event.DefaultScrapEventBus
import com.on.dialog.scrap.impl.viewmodel.ScrapViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val scrapModule = module {
    single { ScrapNavKeyProvider() } bind NavKeyProvider::class
    single<ScrapEventBus> { DefaultScrapEventBus() }

    viewModel {
        ScrapViewModel(
            scrapRepository = get(),
            scrapEventBus = get(),
        )
    }
}
