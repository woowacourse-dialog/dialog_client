package com.on.dialog.data.di

import com.on.dialog.data.repository.AuthDefaultRepository
import com.on.dialog.data.repository.DiscussionDefaultRepository
import com.on.dialog.data.repository.LikeDefaultRepository
import com.on.dialog.data.repository.ParticipantDefaultRepository
import com.on.dialog.data.repository.ScrapDefaultRepository
import com.on.dialog.data.repository.SessionDefaultRepository
import com.on.dialog.data.repository.UserDefaultRepository
import com.on.dialog.domain.repository.AuthRepository
import com.on.dialog.domain.repository.DiscussionRepository
import com.on.dialog.domain.repository.LikeRepository
import com.on.dialog.domain.repository.ParticipantRepository
import com.on.dialog.domain.repository.ScrapRepository
import com.on.dialog.domain.repository.SessionRepository
import com.on.dialog.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<DiscussionRepository> {
        DiscussionDefaultRepository(
            discussionDatasource = get(),
        )
    }
    single<SessionRepository> {
        SessionDefaultRepository(
            cookieStore = get(),
        )
    }
    single<UserRepository> {
        UserDefaultRepository(
            userDatasource = get(),
        )
    }
    single<AuthRepository> {
        AuthDefaultRepository(
            authDatasource = get(),
        )
    }
    single<LikeRepository> {
        LikeDefaultRepository(
            likeDatasource = get(),
        )
    }
    single<ScrapRepository> {
        ScrapDefaultRepository(
            scrapDatasource = get(),
        )
    }
    single<ParticipantRepository> {
        ParticipantDefaultRepository(
            participantDatasource = get(),
        )
    }
}
