package com.on.dialog.feature.mypage.impl.di

import com.on.dialog.feature.mypage.impl.viewmodel.MyPageViewModel
import com.on.dialog.navigation.NavKeyProvider
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val myPageModule = module {
    viewModel {
        MyPageViewModel(
            authRepository = get(),
            userRepository = get(),
            sessionRepository = get(),
            unregisterPushTokenUseCase = get(),
        )
    }

    single { MyPageNavKeyProvider() } bind NavKeyProvider::class
}
