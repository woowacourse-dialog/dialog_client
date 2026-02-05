package com.on.dialog.feature.discussionlist.impl.viewmodel

import com.on.dialog.ui.viewmodel.UiEffect

sealed interface DiscussionListEffect : UiEffect {
    data class ShowSnackbar(
        val message: String,
    ) : DiscussionListEffect
}
