package com.on.dialog.discussiondetail.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import com.on.dialog.ui.viewmodel.UiState

@Immutable
data class DiscussionDetailState(
    val isLoading: Boolean = true,
    val discussion: DiscussionDetailUiModel? = null,
    val isBookmarked: Boolean = false,
    val isLiked: Boolean = false,
    val likeCount: Int = 0,
    val isParticipating: Boolean = false,
    val isMyDiscussion: Boolean = false,
) : UiState
