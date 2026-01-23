package com.on.dialog.feature.discussionlist.impl.di

import com.on.dialog.navigation.NavKeyProvider
import org.koin.dsl.bind
import org.koin.dsl.module

val discussionListModule = module {
    single { DiscussionListNavKeyProvider() } bind NavKeyProvider::class
}
