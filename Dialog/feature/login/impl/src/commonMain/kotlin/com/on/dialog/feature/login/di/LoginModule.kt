package com.on.dialog.feature.login.di

import com.on.dialog.feature.login.LoginViewModel
import com.on.navigation.NavKeyProvider
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(sessionRepository = get()) }

    single { LoginNavKeyProvider() } bind NavKeyProvider::class
}
