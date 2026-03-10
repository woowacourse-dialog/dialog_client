package com.on.dialog.feature.signup.impl.viewmodel

import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.ui.viewmodel.UiEffect
import org.jetbrains.compose.resources.StringResource

interface SignUpEffect : UiEffect {
    data object ExitSignUp : SignUpEffect

    data object OnLoginSuccess : SignUpEffect
    data object NavigateHome : SignUpEffect

    data class ShowSnackbar(
        val stringResource: StringResource,
        val state: SnackbarState,
    ) : SignUpEffect
}
