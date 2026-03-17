package com.on.dialog.data.repository

import com.on.dialog.domain.repository.ReportRepository
import com.on.dialog.network.datasource.ReportDatasource
import com.on.dialog.network.dto.report.ReportCommentRequest
import com.on.dialog.network.dto.report.ReportDiscussionRequest

internal class DefaultReportRepository(
    private val reportDatasource: ReportDatasource,
) : ReportRepository {
    override suspend fun reportDiscussion(
        discussionId: Long,
        reason: String,
    ): Result<Unit> =
        reportDatasource.reportDiscussion(
            discussionId = discussionId,
            request = ReportDiscussionRequest(reason = reason),
        )

    override suspend fun reportComment(
        discussionId: Long,
        commentId: Long,
        reason: String,
    ): Result<Unit> = reportDatasource.reportComment(
        discussionId = discussionId,
        commentId = commentId,
        request = ReportCommentRequest(reason = reason),
    )
}
