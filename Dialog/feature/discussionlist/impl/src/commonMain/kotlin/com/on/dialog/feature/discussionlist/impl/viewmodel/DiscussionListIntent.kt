package com.on.dialog.feature.discussionlist.impl.viewmodel

import com.on.dialog.feature.discussionlist.impl.model.DiscussionStatusUiModel
import com.on.dialog.feature.discussionlist.impl.model.DiscussionTypeUiModel
import com.on.dialog.feature.discussionlist.impl.model.TrackUiModel
import com.on.dialog.ui.viewmodel.UiIntent

internal sealed interface DiscussionListIntent : UiIntent {
    data class ClickTrackFilter(
        val track: TrackUiModel,
    ) : DiscussionListIntent

    data class ClickDiscussionTypeFilter(
        val discussionType: DiscussionTypeUiModel,
    ) : DiscussionListIntent

    data class ClickDiscussionStatusFilter(
        val discussionStatus: DiscussionStatusUiModel,
    ) : DiscussionListIntent

    data object RefreshList : DiscussionListIntent

    data object LoadNextPage : DiscussionListIntent
}
