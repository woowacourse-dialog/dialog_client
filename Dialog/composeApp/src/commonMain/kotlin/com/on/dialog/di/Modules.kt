package com.on.dialog.di

import com.on.dialog.core.local.di.localModule
import com.on.dialog.data.di.dataModule
import com.on.dialog.data.di.repositoryModule
import com.on.dialog.feature.login.di.loginModule
import com.on.dialog.feature.main.impl.di.mainModule
import com.on.dialog.feature.mypage.di.myPageModule
import com.on.dialog.impl.di.discussionDetailModule
import com.on.dialog.navigation.SavedStateConfigurationProvider
import com.on.impl.di.scrapModule
import com.on.navigation.NavKeyProvider
import com.on.network.di.datasourceModule
import com.on.network.di.networkModule
import com.on.network.di.serviceModule
import org.koin.dsl.module

val coreModule =
    module {
        includes(
            networkModule,
            serviceModule,
            datasourceModule,
            repositoryModule,
            localModule,
            dataModule,
            discussionDetailModule,
        )
    }

val featureModule =
    module {
        includes(mainModule, scrapModule, myPageModule, loginModule)
    }

val appModule =
    module {
        includes(coreModule, featureModule)

        single {
            SavedStateConfigurationProvider(
                providers = getAll<NavKeyProvider>(),
            )
        }
    }
