package com.on.dialog.feature.login.di

import com.on.dialog.feature.login.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(get()) }
}
