package com.on.dialog.feature.discussionlist.impl.di

import com.on.dialog.feature.discussionlist.impl.navigation.DiscussionListNavKeyProvider
import com.on.dialog.feature.discussionlist.impl.viewmodel.DiscussionListViewModel
import com.on.dialog.navigation.NavKeyProvider
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val discussionListModule = module {
    viewModel {
        DiscussionListViewModel(
            discussionRepository = get(),
            authRepository = get(),
            sessionRepository = get(),
        )
    }

    single { DiscussionListNavKeyProvider() } bind NavKeyProvider::class
}
