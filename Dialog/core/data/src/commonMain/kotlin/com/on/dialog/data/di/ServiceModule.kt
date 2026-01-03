package com.on.dialog.data.di

import com.on.dialog.data.service.DiscussionCreateService
import com.on.dialog.data.service.DiscussionService
import com.on.dialog.data.service.DiscussionSummaryService
import com.on.dialog.data.service.DiscussionUpdateService
import com.on.dialog.data.serviceImpl.DefaultDiscussionCreateService
import com.on.dialog.data.serviceImpl.DefaultDiscussionService
import com.on.dialog.data.serviceImpl.DefaultDiscussionSummaryService
import com.on.dialog.data.serviceImpl.DefaultDiscussionUpdateService
import org.koin.dsl.module

val serviceModule = module {
    single<DiscussionCreateService> {
        DefaultDiscussionCreateService(
            client = get(),
        )
    }

    single<DiscussionService> {
        DefaultDiscussionService(
            client = get(),
        )
    }

    single<DiscussionSummaryService> {
        DefaultDiscussionSummaryService(
            client = get(),
        )
    }

    single<DiscussionUpdateService> {
        DefaultDiscussionUpdateService(
            client = get(),
        )
    }
}
