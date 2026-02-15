package com.on.dialog.discussiondetail.impl.di

import com.on.dialog.discussiondetail.impl.navigation.DiscussionDetailNavKeyProvider
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailViewModel
import com.on.dialog.navigation.NavKeyProvider
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val discussionDetailModule = module {
    single { DiscussionDetailNavKeyProvider() } bind NavKeyProvider::class

    viewModelOf(::DiscussionDetailViewModel)
}
