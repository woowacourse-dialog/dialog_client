package com.on.dialog.di

import org.koin.dsl.module

val coreModule =
    module { }

val featureModule =
    module { }

val appModule =
    module {
        includes(coreModule, featureModule)
    }
