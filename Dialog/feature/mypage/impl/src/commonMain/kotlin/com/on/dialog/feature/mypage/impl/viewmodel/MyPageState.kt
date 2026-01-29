package com.on.dialog.feature.mypage.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.model.common.Track
import com.on.dialog.ui.viewmodel.UiState

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
