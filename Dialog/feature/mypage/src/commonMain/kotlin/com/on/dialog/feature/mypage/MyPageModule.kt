package com.on.dialog.feature.mypage

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val myPageModule = module {
    viewModel { MyPageViewModel(get(), get()) }
}
