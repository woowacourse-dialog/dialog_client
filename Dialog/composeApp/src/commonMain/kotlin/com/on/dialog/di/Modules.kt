package com.on.dialog.di

import com.on.network.di.networkModule
import com.on.network.di.serviceModule
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val coreModule =
    module {
        includes(networkModule, serviceModule)
    }

val featureModule =
    module { }

val appModule =
    module {
        includes(coreModule, featureModule)
    }
