package com.on.dialog.di

import com.on.dialog.data.di.dataModule
import com.on.dialog.discussiondetail.impl.di.discussionDetailModule
import com.on.dialog.feature.discussionlist.impl.di.discussionListModule
import com.on.dialog.feature.login.di.loginModule
import com.on.dialog.feature.mypage.di.myPageModule
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
        )
    }

val appModule =
    module {
        includes(coreModule, featureModule)
    }
