package com.on.dialog.feature.creatediscussion.impl.di

import com.on.dialog.feature.creatediscussion.impl.navigation.CreateDiscussionNavKeyProvider
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionViewModel
import com.on.dialog.navigation.NavKeyProvider
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val createDiscussionModule = module {
    viewModel { CreateDiscussionViewModel(discussionRepository = get()) }
    single { CreateDiscussionNavKeyProvider() } bind NavKeyProvider::class
}
