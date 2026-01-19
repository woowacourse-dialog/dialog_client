package com.on.dialog.feature.mypage

import com.on.dialog.ui.viewmodel.UiIntent

sealed interface MyPageUiIntent : UiIntent {
    data object LoadMyPage : MyPageUiIntent

    data object Logout : MyPageUiIntent
}
