package com.on.dialog.di

import com.on.dialog.data.di.dataModule
import com.on.dialog.discussiondetail.impl.di.discussionDetailModule
import com.on.dialog.domain.usecase.pushtoken.RegisterPushTokenUseCase
import com.on.dialog.domain.usecase.pushtoken.UnregisterPushTokenUseCase
import com.on.dialog.feature.discussionlist.impl.di.discussionListModule
import com.on.dialog.feature.login.impl.di.loginModule
import com.on.dialog.feature.mycreated.impl.di.myCreatedModule
import com.on.dialog.feature.mypage.impl.di.myPageModule
import com.on.dialog.feature.signup.impl.di.signUpModule
import com.on.dialog.main.di.mainModule
import com.on.dialog.scrap.impl.di.scrapModule
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule =
    module {
        factoryOf(::RegisterPushTokenUseCase)
        factoryOf(::UnregisterPushTokenUseCase)
    }

val coreModule =
    module {
        includes(dataModule, useCaseModule)
    }

val featureModule =
    module {
        includes(
            mainModule,
            discussionListModule,
            discussionDetailModule,
            scrapModule,
            myPageModule,
            loginModule,
            signUpModule,
            myCreatedModule,
        )
    }

val appModule =
    module {
        includes(coreModule, featureModule)
    }
