package com.on.dialog.discussiondetail.impl.di

import com.on.dialog.discussiondetail.impl.usecase.GenerateDiscussionSummaryUseCase
import com.on.dialog.discussiondetail.impl.navigation.DiscussionDetailNavKeyProvider
import com.on.dialog.discussiondetail.impl.usecase.ToggleDiscussionBookmarkUseCase
import com.on.dialog.discussiondetail.impl.usecase.ToggleDiscussionLikeUseCase
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailViewModel
import com.on.dialog.navigation.NavKeyProvider
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val discussionDetailModule = module {
    single { DiscussionDetailNavKeyProvider() } bind NavKeyProvider::class
    factory { GenerateDiscussionSummaryUseCase(discussionRepository = get()) }
    factory { ToggleDiscussionBookmarkUseCase(scrapRepository = get()) }
    factory { ToggleDiscussionLikeUseCase(likeRepository = get()) }

    viewModel { params ->
        DiscussionDetailViewModel(
            discussionId = params.get(),
            discussionRepository = get(),
            likeRepository = get(),
            scrapRepository = get(),
            commentRepository = get(),
            participantRepository = get(),
            sessionRepository = get(),
            generateDiscussionSummaryUseCase = get(),
            toggleDiscussionBookmarkUseCase = get(),
            toggleDiscussionLikeUseCase = get(),
        )
    }
}
