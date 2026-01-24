package com.on.dialog.network.di

import com.on.dialog.network.service.AuthService
import com.on.dialog.network.service.DiscussionService
import com.on.dialog.network.service.UserService
import com.on.dialog.network.service.createAuthService
import com.on.dialog.network.service.createDiscussionService
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
}
