package com.on.dialog.feature.login.impl.di

import com.on.dialog.feature.login.impl.viewmodel.LoginViewModel
import com.on.dialog.navigation.NavKeyProvider
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val loginModule = module {
    viewModel {
        LoginViewModel(
            sessionRepository = get(),
            userRepository = get(),
            authRepository = get(),
            appleSignInHandler = get(qualifier = named<AuthQualifier.Apple>()),
        )
    }

    single { LoginNavKeyProvider() } bind NavKeyProvider::class

    registerAppleSignInHandler()
}

expect fun Module.registerAppleSignInHandler()
