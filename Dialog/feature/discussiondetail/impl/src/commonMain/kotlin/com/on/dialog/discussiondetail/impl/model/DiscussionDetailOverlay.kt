package com.on.dialog.discussiondetail.impl.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
internal sealed interface DiscussionDetailOverlay {
    @Serializable
    data object None : DiscussionDetailOverlay

    @Serializable
    data class Comment(
        val type: CommentType,
    ) : DiscussionDetailOverlay

    @Serializable
    data class Delete(
        val type: DeleteType,
    ) : DiscussionDetailOverlay

    @Serializable
    data class Report(
        val type: ReportType,
    ) : DiscussionDetailOverlay
}
