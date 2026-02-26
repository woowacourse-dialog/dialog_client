package com.on.dialog.discussiondetail.impl.viewmodel

import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.ui.viewmodel.UiEffect
import org.jetbrains.compose.resources.StringResource

sealed interface DiscussionDetailEffect : UiEffect {
    data class ShowSnackbar(
        val message: StringResource,
        val state: SnackbarState,
    ) : DiscussionDetailEffect

    data object NavigateHome : DiscussionDetailEffect
}
