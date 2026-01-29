package com.on.dialog.di

import com.on.dialog.core.local.di.localModule
import com.on.dialog.data.di.dataModule
import com.on.dialog.data.di.repositoryModule
import com.on.dialog.discussiondetail.impl.di.discussionDetailModule
import com.on.dialog.feature.discussionlist.impl.di.discussionListModule
import com.on.dialog.feature.login.impl.di.loginModule
import com.on.dialog.feature.mypage.impl.di.myPageModule
import com.on.dialog.navigation.NavKeyProvider
import com.on.dialog.navigation.SavedStateConfigurationProvider
import com.on.dialog.network.di.datasourceModule
import com.on.dialog.network.di.networkModule
import com.on.dialog.network.di.serviceModule
import com.on.dialog.scrap.impl.di.scrapModule
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
        includes(discussionListModule, scrapModule, myPageModule, loginModule)
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
