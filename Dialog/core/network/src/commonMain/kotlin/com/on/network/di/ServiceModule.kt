package com.on.network.di

import com.on.network.service.*
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

val serviceModule = module {
    single<DiscussionService> {
        get<Ktorfit>().create()
    }
}
