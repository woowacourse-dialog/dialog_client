package com.on.dialog.di

import com.on.dialog.data.di.dataModule
import com.on.dialog.discussiondetail.impl.di.discussionDetailModule
import com.on.dialog.feature.discussionlist.impl.di.discussionListModule
import com.on.dialog.feature.login.impl.di.loginModule
import com.on.dialog.feature.mypage.impl.di.myPageModule
import com.on.dialog.feature.signup.impl.di.signUpModule
import com.on.dialog.main.di.mainModule
import com.on.dialog.scrap.impl.di.scrapModule
import org.koin.dsl.module

val coreModule =
    module {
        includes(dataModule)
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
        )
    }

val appModule =
    module {
        includes(coreModule, featureModule)
    }
