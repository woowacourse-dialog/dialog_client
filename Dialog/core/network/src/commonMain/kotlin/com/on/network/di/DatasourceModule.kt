package com.on.network.di

import com.on.network.datasource.AuthDatasource
import com.on.network.datasource.DiscussionDatasource
import com.on.network.datasourceimpl.AuthRemoteDatasource
import com.on.network.datasourceimpl.DiscussionRemoteDatasource
import org.koin.dsl.module

val datasourceModule = module {
    single<DiscussionDatasource> {
        DiscussionRemoteDatasource(get())
    }
    single<AuthDatasource> {
        AuthRemoteDatasource(get())
    }
}
