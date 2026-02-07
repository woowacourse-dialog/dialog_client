package com.on.dialog.feature.signup.impl.viewmodel

import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.ui.viewmodel.UiEffect

interface SignUpEffect : UiEffect {
    data object NavigateHome : SignUpEffect

    data class ShowSnackbar(
        val message: String,
        val state: SnackbarState,
    ) : SignUpEffect
}