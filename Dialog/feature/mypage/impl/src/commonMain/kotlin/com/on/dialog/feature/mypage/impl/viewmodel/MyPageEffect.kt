package com.on.dialog.feature.mypage.impl.viewmodel

import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.ui.viewmodel.UiEffect

sealed interface MyPageEffect : UiEffect {
    data class ShowSnackbar(
        val message: String,
        val state: SnackbarState,
    ) : MyPageEffect
}
