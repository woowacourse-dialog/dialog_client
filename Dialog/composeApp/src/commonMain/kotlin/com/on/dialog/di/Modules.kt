package com.on.dialog.di

import com.on.network.di.networkModule
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val coreModule =
    module {
        includes(networkModule)
    }

val featureModule =
    module { }

val appModule =
    module {
        includes(coreModule, featureModule)
    }
