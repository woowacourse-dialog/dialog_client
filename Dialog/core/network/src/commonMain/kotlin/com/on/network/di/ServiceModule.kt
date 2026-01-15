package com.on.network.di

import com.on.network.service.AuthService
import com.on.network.service.DiscussionService
import com.on.network.service.UserService
import com.on.network.service.createAuthService
import com.on.network.service.createDiscussionService
import com.on.network.service.createUserService
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
