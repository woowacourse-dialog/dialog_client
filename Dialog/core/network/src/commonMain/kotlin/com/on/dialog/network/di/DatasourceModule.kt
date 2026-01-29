package com.on.dialog.network.di

import com.on.dialog.network.datasource.AuthDatasource
import com.on.dialog.network.datasource.DiscussionDatasource
import com.on.dialog.network.datasource.UserDatasource
import com.on.dialog.network.datasourceimpl.AuthRemoteDatasource
import com.on.dialog.network.datasourceimpl.DiscussionRemoteDatasource
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
}
