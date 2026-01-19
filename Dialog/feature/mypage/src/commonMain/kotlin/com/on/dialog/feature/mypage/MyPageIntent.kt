package com.on.dialog.feature.mypage

import com.on.dialog.ui.viewmodel.UiIntent

sealed interface MyPageIntent : UiIntent {
    data object LoadMyPage : MyPageIntent

    data object Logout : MyPageIntent
}
