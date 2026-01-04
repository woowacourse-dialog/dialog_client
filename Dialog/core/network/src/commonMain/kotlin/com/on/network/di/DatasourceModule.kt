package com.on.network.di

import com.on.network.datasource.DiscussionCreateDatasource
import com.on.network.datasource.DiscussionDatasource
import com.on.network.datasource.DiscussionSummaryDatasource
import com.on.network.datasource.DiscussionUpdateDatasource
import com.on.network.datasourceimpl.DiscussCreateRemoteDatasource
import com.on.network.datasourceimpl.DiscussionRemoteDatasource
import com.on.network.datasourceimpl.DiscussionSummaryRemoteDatasource
import com.on.network.datasourceimpl.DiscussionUpdateRemoteDatasource
import org.koin.dsl.module

val datasourceModule = module {
    single<DiscussionCreateDatasource>{
        DiscussCreateRemoteDatasource(get())
    }
    single<DiscussionDatasource> {
        DiscussionRemoteDatasource(get())
    }
    single<DiscussionSummaryDatasource> {
        DiscussionSummaryRemoteDatasource(get())
    }
    single<DiscussionUpdateDatasource>{
        DiscussionUpdateRemoteDatasource(get())
    }
}