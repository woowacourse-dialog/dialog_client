package com.on.dialog.discussiondetail.impl.model

internal sealed interface ReportType {
    data object Discussion : ReportType

    data class Comment(
        val commentId: Long,
    ) : ReportType
}
