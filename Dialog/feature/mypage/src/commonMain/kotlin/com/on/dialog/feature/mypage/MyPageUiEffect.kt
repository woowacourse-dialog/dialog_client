package com.on.dialog.feature.mypage

import com.on.dialog.ui.viewmodel.UiEffect

sealed interface MyPageUiEffect : UiEffect {
    data class ShowError(
        val message: String,
    ) : MyPageUiEffect
}
