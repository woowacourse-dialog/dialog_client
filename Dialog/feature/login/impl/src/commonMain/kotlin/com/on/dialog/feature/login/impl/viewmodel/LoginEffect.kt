package com.on.dialog.feature.login.impl.viewmodel

import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.ui.viewmodel.UiEffect

sealed interface LoginEffect : UiEffect {
    data object GoBack : LoginEffect

    data object NavigateToSignUp : LoginEffect

    data class ShowSnackbar(
        val message: String,
        val state: SnackbarState,
    ) : LoginEffect
}
