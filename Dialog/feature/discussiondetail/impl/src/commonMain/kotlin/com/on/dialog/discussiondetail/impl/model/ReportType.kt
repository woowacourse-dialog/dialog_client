package com.on.dialog.discussiondetail.impl.model

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface ReportType {
    @Serializable
    data object Discussion : ReportType

    @Serializable
    data class Comment(
        val commentId: Long,
    ) : ReportType
}
