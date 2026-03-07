package com.on.dialog.feature.creatediscussion.impl.viewmodel

import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.ui.viewmodel.UiEffect

internal sealed interface CreateDiscussionEffect : UiEffect {
    data object GoBack : CreateDiscussionEffect

    data class ShowSnackbar(
        val message: String,
        val state: SnackbarState = SnackbarState.DEFAULT,
    ) : CreateDiscussionEffect
}
