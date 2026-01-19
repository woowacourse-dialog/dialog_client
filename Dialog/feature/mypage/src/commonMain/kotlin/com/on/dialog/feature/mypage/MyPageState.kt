package com.on.dialog.feature.mypage

import com.on.dialog.ui.viewmodel.UiState

data class MyPageState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true,
    val imageUrl: String = "",
    val nickname: String = "",
    val track: String = "",
    val githubId: String = "",
    val isNotificationEnable: Boolean = false,
) : UiState
