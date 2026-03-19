package com.on.dialog.domain.repository

interface ReportRepository {
    suspend fun reportDiscussion(
        discussionId: Long,
        reason: String,
    ): Result<Unit>

    suspend fun reportComment(
        discussionId: Long,
        commentId: Long,
        reason: String,
    ): Result<Unit>
}
