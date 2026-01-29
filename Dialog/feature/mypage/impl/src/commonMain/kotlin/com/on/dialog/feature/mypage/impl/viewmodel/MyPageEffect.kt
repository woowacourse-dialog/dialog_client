package com.on.dialog.feature.mypage.impl.viewmodel

import com.on.dialog.ui.viewmodel.UiEffect

sealed interface MyPageEffect : UiEffect {
    data class ShowError(
        val message: String,
    ) : MyPageEffect
}
