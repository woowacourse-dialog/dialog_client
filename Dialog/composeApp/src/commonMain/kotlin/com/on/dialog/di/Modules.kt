package com.on.dialog.di

import com.on.dialog.core.local.di.localModule
import com.on.dialog.data.di.repositoryModule
import com.on.dialog.feature.login.di.loginModule
import com.on.dialog.feature.mypage.myPageModule
import com.on.network.di.datasourceModule
import com.on.network.di.networkModule
import com.on.network.di.serviceModule
import org.koin.dsl.module

val coreModule =
    module {
        includes(networkModule, serviceModule, datasourceModule, repositoryModule, localModule)
    }

val featureModule =
    module {
        includes(loginModule, myPageModule)
    }

val appModule =
    module {
        includes(coreModule, featureModule)
    }
