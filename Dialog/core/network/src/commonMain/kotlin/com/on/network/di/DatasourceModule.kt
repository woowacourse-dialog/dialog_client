package com.on.network.di

import com.on.network.datasource.DiscussionCreateDatasource
import com.on.network.datasource.DiscussionDatasource
import com.on.network.datasource.DiscussionSummaryDatasource
import com.on.network.datasource.DiscussionUpdateDatasource
import com.on.network.datasourceimpl.RemoteDiscussCreateDatasource
import com.on.network.datasourceimpl.RemoteDiscussionDatasource
import com.on.network.datasourceimpl.RemoteDiscussionSummaryDatasource
import com.on.network.datasourceimpl.RemoteDiscussionUpdateDatasource
import org.koin.dsl.module

val datasourceModule = module {
    single<DiscussionCreateDatasource>{
        RemoteDiscussCreateDatasource(get())
    }
    single<DiscussionDatasource> {
        RemoteDiscussionDatasource(get())
    }
    single<DiscussionSummaryDatasource> {
        RemoteDiscussionSummaryDatasource(get())
    }
    single<DiscussionUpdateDatasource>{
        RemoteDiscussionUpdateDatasource(get())
    }
}