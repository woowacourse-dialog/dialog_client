package com.on.dialog.feature.login.di

import com.on.api.LoginNavigator
import com.on.dialog.feature.login.LoginViewModel
import com.on.dialog.feature.login.navigation.DefaultLoginNavigation
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(sessionRepository = get()) }

    single<LoginNavigator> {
        DefaultLoginNavigation(navigator = get())
    }
}
