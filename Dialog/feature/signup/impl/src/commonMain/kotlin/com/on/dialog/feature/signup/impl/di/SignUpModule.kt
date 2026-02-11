package com.on.dialog.feature.signup.impl.di

import com.on.dialog.feature.signup.impl.navigation.SignUpNavKeyProvider
import com.on.dialog.feature.signup.impl.viewmodel.SignUpViewModel
import com.on.dialog.navigation.NavKeyProvider
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val signUpModule = module {
    viewModelOf(::SignUpViewModel)

    single { SignUpNavKeyProvider() } bind NavKeyProvider::class
}
