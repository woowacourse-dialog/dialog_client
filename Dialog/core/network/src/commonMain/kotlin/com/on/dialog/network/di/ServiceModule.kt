package com.on.dialog.network.di

import com.on.dialog.network.service.AuthService
import com.on.dialog.network.service.CommentService
import com.on.dialog.network.service.DiscussionService
import com.on.dialog.network.service.FirebaseService
import com.on.dialog.network.service.LikeService
import com.on.dialog.network.service.ParticipantService
import com.on.dialog.network.service.ReportService
import com.on.dialog.network.service.ScrapService
import com.on.dialog.network.service.UserService
import com.on.dialog.network.service.createAuthService
import com.on.dialog.network.service.createCommentService
import com.on.dialog.network.service.createDiscussionService
import com.on.dialog.network.service.createFirebaseService
import com.on.dialog.network.service.createLikeService
import com.on.dialog.network.service.createParticipantService
import com.on.dialog.network.service.createReportService
import com.on.dialog.network.service.createScrapService
import com.on.dialog.network.service.createUserService
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

val serviceModule = module {
    single<DiscussionService> {
        get<Ktorfit>().createDiscussionService()
    }
    single<AuthService> {
        get<Ktorfit>().createAuthService()
    }
    single<UserService> {
        get<Ktorfit>().createUserService()
    }
    single<LikeService> {
        get<Ktorfit>().createLikeService()
    }
    single<ScrapService> {
        get<Ktorfit>().createScrapService()
    }
    single<ParticipantService> {
        get<Ktorfit>().createParticipantService()
    }
    single<CommentService> {
        get<Ktorfit>().createCommentService()
    }
    single<ReportService> {
        get<Ktorfit>().createReportService()
    }
    single<FirebaseService> {
        get<Ktorfit>().createFirebaseService()
    }
}
