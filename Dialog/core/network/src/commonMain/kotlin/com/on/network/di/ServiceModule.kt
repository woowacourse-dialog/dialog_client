package com.on.network.di

import com.on.network.service.*
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

val serviceModule = module {

    single<DiscussCreateService> {
        get<Ktorfit>().create()
    }

    single<DiscussionService> {
        get<Ktorfit>().create()
    }

    single<DiscussionSummaryService> {
        get<Ktorfit>().create()
    }

    single<DiscussionUpdateService> {
        get<Ktorfit>().create()
    }
}
