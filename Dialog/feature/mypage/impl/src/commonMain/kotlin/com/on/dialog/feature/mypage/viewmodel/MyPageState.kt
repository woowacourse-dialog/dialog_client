package com.on.dialog.feature.mypage.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.ui.viewmodel.UiState
import com.on.model.common.Track

@Immutable
data class MyPageState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true,
    val imageUrl: String = "",
    val nickname: String = "",
    val track: Track = Track.ANDROID,
    val githubId: String = "",
    val isNotificationEnable: Boolean = false,
) : UiState
