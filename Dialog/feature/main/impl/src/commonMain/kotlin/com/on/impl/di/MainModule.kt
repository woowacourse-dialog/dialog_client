package com.on.impl.di

import com.on.api.MainNavigator
import com.on.impl.navigation.DefaultMainNavigator
import org.koin.dsl.module

val mainModule = module {
    single<MainNavigator> {
        DefaultMainNavigator(navigator = get())
    }
}
