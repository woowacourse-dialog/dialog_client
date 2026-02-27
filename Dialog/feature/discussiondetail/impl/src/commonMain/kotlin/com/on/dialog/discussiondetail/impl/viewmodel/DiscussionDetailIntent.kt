package com.on.dialog.discussiondetail.impl.viewmodel

import com.on.dialog.ui.viewmodel.UiIntent

sealed interface DiscussionDetailIntent : UiIntent {
    data object ToggleBookmark : DiscussionDetailIntent

    data object ToggleLike : DiscussionDetailIntent

    data object Participate : DiscussionDetailIntent

    data object GenerateSummary : DiscussionDetailIntent
}
