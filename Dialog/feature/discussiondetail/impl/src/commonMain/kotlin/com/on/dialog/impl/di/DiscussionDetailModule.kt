package com.on.dialog.impl.di

import com.on.dialog.impl.navigation.DiscussionDetailNavKeyProvider
import com.on.navigation.NavKeyProvider
import org.koin.dsl.bind
import org.koin.dsl.module

val discussionDetailModule = module {
    single { DiscussionDetailNavKeyProvider() } bind NavKeyProvider::class
}
