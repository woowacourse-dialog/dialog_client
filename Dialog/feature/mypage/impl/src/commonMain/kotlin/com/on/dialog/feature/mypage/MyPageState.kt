package com.on.dialog.feature.mypage

import androidx.compose.runtime.Immutable
import com.on.dialog.ui.viewmodel.UiState

@Immutable
data class MyPageState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true,
    val imageUrl: String = "",
    val nickname: String = "",
    val track: String = "",
    val githubId: String = "",
    val isNotificationEnable: Boolean = false,
) : UiState
