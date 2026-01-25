package com.on.dialog.feature.mypage.viewmodel

import com.on.dialog.ui.viewmodel.UiIntent
import com.on.model.common.Track

sealed interface MyPageIntent : UiIntent {
    data object CheckLoginStatus : MyPageIntent

    data object Logout : MyPageIntent

    data class EditProfile(
        val nickname: String,
        val track: Track,
    ) : MyPageIntent
}
