package com.on.dialog.data.di

import com.on.dialog.data.repository.DiscussionDefaultRepository
import com.on.dialog.domain.repository.DiscussionRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<DiscussionRepository> {
        DiscussionDefaultRepository(
            discussionDatasource = get(),
        )
    }
}
