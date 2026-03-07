package com.on.dialog.feature.creatediscussion.impl.di

import com.on.dialog.feature.creatediscussion.api.CreateDiscussionNavKey
import com.on.dialog.navigation.NavKeyProvider
import org.koin.dsl.bind
import org.koin.dsl.module

val createDiscussionModule = module {
    single { CreateDiscussionNavKey } bind NavKeyProvider::class


}