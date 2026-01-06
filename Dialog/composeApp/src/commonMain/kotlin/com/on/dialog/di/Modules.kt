package com.on.dialog.di

import com.on.dialog.data.di.repositoryModule
import com.on.network.di.datasourceModule
import com.on.network.di.networkModule
import com.on.network.di.serviceModule
import org.koin.dsl.module

val coreModule =
    module {
        includes(networkModule, serviceModule, datasourceModule, repositoryModule)
    }

val featureModule =
    module { }

val appModule =
    module {
        includes(coreModule, featureModule)
    }
