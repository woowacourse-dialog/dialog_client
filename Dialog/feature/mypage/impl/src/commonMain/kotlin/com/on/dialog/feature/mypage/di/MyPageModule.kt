package com.on.dialog.feature.mypage.di

import com.on.dialog.feature.mypage.viewmodel.MyPageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val myPageModule = module {
    viewModel { MyPageViewModel(authRepository = get(), userRepository = get()) }
}
