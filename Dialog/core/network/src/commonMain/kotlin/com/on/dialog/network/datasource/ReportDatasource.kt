package com.on.dialog.network.datasource

import com.on.dialog.network.dto.report.ReportCommentRequest
import com.on.dialog.network.dto.report.ReportDiscussionRequest

interface ReportDatasource {
    suspend fun reportDiscussion(
        discussionId: Long,
        request: ReportDiscussionRequest,
    ): Result<Unit>

    suspend fun reportComment(
        discussionId: Long,
        commentId: Long,
        request: ReportCommentRequest,
    ): Result<Unit>
}
