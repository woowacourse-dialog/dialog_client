package com.on.dialog.di

import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val coreModule =
    module { }

val featureModule =
    module { }

val appModule =
    module {
        includes(coreModule, featureModule)
    }
