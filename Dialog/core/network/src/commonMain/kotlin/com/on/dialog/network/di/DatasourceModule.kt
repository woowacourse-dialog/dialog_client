package com.on.dialog.network.di

import com.on.dialog.network.datasource.AuthDatasource
import com.on.dialog.network.datasource.CommentDatasource
import com.on.dialog.network.datasource.DiscussionDatasource
import com.on.dialog.network.datasource.LikeDatasource
import com.on.dialog.network.datasource.ParticipantDatasource
import com.on.dialog.network.datasource.ReportDatasource
import com.on.dialog.network.datasource.ScrapDatasource
import com.on.dialog.network.datasource.UserDatasource
import com.on.dialog.network.datasourceimpl.AuthRemoteDatasource
import com.on.dialog.network.datasourceimpl.CommentRemoteDatasource
import com.on.dialog.network.datasourceimpl.DiscussionRemoteDatasource
import com.on.dialog.network.datasourceimpl.LikeRemoteDatasource
import com.on.dialog.network.datasourceimpl.ParticipantRemoteDatasource
import com.on.dialog.network.datasourceimpl.ReportRemoteDatasource
import com.on.dialog.network.datasourceimpl.ScrapRemoteDatasource
import com.on.dialog.network.datasourceimpl.UserRemoteDatasource
import org.koin.dsl.module

val datasourceModule = module {
    single<DiscussionDatasource> {
        DiscussionRemoteDatasource(discussionService = get())
    }
    single<AuthDatasource> {
        AuthRemoteDatasource(authService = get())
    }
    single<UserDatasource> {
        UserRemoteDatasource(userService = get())
    }
    single<ScrapDatasource> {
        ScrapRemoteDatasource(scrapService = get())
    }
    single<LikeDatasource> {
        LikeRemoteDatasource(likeService = get())
    }
    single<ScrapDatasource> {
        ScrapRemoteDatasource(scrapService = get())
    }
    single<ParticipantDatasource> {
        ParticipantRemoteDatasource(participantService = get())
    }
    single<CommentDatasource> {
        CommentRemoteDatasource(service = get())
    }
    single<ReportDatasource> {
        ReportRemoteDatasource(service = get())
    }
}
