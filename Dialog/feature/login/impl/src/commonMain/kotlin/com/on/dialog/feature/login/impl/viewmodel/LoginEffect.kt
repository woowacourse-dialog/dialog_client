package com.on.dialog.feature.login.impl.viewmodel

import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.ui.viewmodel.UiEffect
import org.jetbrains.compose.resources.StringResource

sealed interface LoginEffect : UiEffect {
    data object GoBack : LoginEffect

    data class NavigateToSignUp(
        val jsessionId: String,
    ) : LoginEffect

    data class ShowSnackbar(
        val stringResource: StringResource,
        val state: SnackbarState,
    ) : LoginEffect
}
