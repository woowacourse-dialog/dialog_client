package com.on.dialog.feature.mycreated.impl.di

import com.on.dialog.feature.mycreated.impl.navigation.MyCreatedNavKeyProvider
import com.on.dialog.feature.mycreated.impl.viewmodel.MyCreatedViewModel
import com.on.dialog.navigation.NavKeyProvider
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val myCreatedModule = module {
    viewModel { MyCreatedViewModel(discussionRepository = get()) }

    single { MyCreatedNavKeyProvider() } bind NavKeyProvider::class
}
