package com.on.dialog.discussiondetail.impl.viewmodel

import com.on.dialog.discussiondetail.impl.model.CommentType
import com.on.dialog.discussiondetail.impl.model.ReportReasonUiModel
import com.on.dialog.ui.viewmodel.UiIntent

internal sealed interface DiscussionDetailIntent : UiIntent {
    data object ToggleBookmark : DiscussionDetailIntent

    data object ToggleLike : DiscussionDetailIntent

    data object Participate : DiscussionDetailIntent

    data object GenerateSummary : DiscussionDetailIntent

    data class OnReportDiscussion(
        val reason: ReportReasonUiModel,
    ) : DiscussionDetailIntent

    data class OnSubmitComment(
        val content: String,
    ) : DiscussionDetailIntent

    data class OnSubmitReply(
        val commentId: Long,
        val content: String,
    ) : DiscussionDetailIntent

    data class OnEditComment(
        val commentId: Long,
        val content: String,
    ) : DiscussionDetailIntent

    data class OnDeleteComment(
        val commentId: Long,
    ) : DiscussionDetailIntent

    data class OpenCommentEditor(
        val type: CommentType,
    ) : DiscussionDetailIntent

    data object CloseCommentEditor : DiscussionDetailIntent

    data class OpenDeleteCommentDialog(
        val commentId: Long,
    ) : DiscussionDetailIntent

    data object CloseDeleteCommentDialog : DiscussionDetailIntent

    data object OpenReportDiscussionDialog : DiscussionDetailIntent

    data object CloseReportDiscussionDialog : DiscussionDetailIntent
}
