package com.on.dialog.discussiondetail.impl.model

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface DiscussionDetailOverlay {
    data class Comment(
        val type: CommentType,
    ) : DiscussionDetailOverlay

    data class Delete(
        val type: DeleteType,
    ) : DiscussionDetailOverlay

    data class Report(
        val type: ReportType,
    ) : DiscussionDetailOverlay
}
