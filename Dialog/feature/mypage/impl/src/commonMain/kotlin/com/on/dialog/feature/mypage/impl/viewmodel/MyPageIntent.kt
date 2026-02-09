package com.on.dialog.feature.mypage.impl.viewmodel

import com.on.dialog.model.common.Track
import com.on.dialog.ui.viewmodel.UiIntent

sealed interface MyPageIntent : UiIntent {
    data object CheckLoginStatus : MyPageIntent

    data object Logout : MyPageIntent

    data class EditProfile(
        val nickname: String,
        val track: Track,
    ) : MyPageIntent

    data class EditProfileImage(
        val uri: String,
    ) : MyPageIntent

    data object DeleteAccount : MyPageIntent
}
