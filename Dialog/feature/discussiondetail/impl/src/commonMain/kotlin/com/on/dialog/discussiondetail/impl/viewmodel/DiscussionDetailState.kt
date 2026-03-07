package com.on.dialog.discussiondetail.impl.viewmodel

import androidx.compose.runtime.Immutable
import com.on.dialog.discussiondetail.impl.model.DiscussionCommentUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionStatusUiModel
import com.on.dialog.model.discussion.content.DiscussionType
import com.on.dialog.ui.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class DiscussionDetailState(
    val isLoading: Boolean = true,
    val isGeneratingSummary: Boolean = false,
    val discussion: DiscussionDetailUiModel? = null,
    val isBookmarked: Boolean = false,
    val isLiked: Boolean = false,
    val likeCount: Int = 0,
    val isParticipating: Boolean = false,
    val isMyDiscussion: Boolean = false,
    val comments: ImmutableList<DiscussionCommentUiModel> = persistentListOf(),
) : UiState {
    val isShowSummary: Boolean =
        discussion?.discussionType == DiscussionType.ONLINE && discussion.status == DiscussionStatusUiModel.DISCUSSION_COMPLETE

    val isShowParticipateButton: Boolean = discussion?.discussionType == DiscussionType.OFFLINE

    val totalCommentCount: Int =
        comments.size + comments.sumOf { comment -> comment.childComments.size }
}
