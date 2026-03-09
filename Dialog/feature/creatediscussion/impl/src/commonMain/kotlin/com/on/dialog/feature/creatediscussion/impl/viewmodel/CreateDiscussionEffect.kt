package com.on.dialog.feature.creatediscussion.impl.viewmodel

import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.ui.viewmodel.UiEffect
import org.jetbrains.compose.resources.StringResource

internal sealed interface CreateDiscussionEffect : UiEffect {
    data class NavigateToDetail(
        val discussionId: Long,
    ) : CreateDiscussionEffect

    data class ShowSnackbar(
        val message: StringResource,
        val state: SnackbarState = SnackbarState.DEFAULT,
    ) : CreateDiscussionEffect
}
