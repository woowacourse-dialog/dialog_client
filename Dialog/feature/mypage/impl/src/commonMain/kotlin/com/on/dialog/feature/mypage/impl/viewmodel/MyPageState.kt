package com.on.dialog.feature.mypage.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.feature.mypage.impl.model.UserInfoUiModel
import com.on.dialog.ui.viewmodel.UiState

@Immutable
data class MyPageState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true,
    val imageUrl: String = "",
    val userInfo: UserInfoUiModel = UserInfoUiModel(),
) : UiState
