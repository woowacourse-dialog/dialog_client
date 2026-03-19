package com.on.dialog.network.datasourceimpl

import com.on.dialog.network.common.safeApiCall
import com.on.dialog.network.datasource.ReportDatasource
import com.on.dialog.network.dto.report.ReportCommentRequest
import com.on.dialog.network.dto.report.ReportDiscussionRequest
import com.on.dialog.network.service.ReportService

class ReportRemoteDatasource(
    private val service: ReportService,
) : ReportDatasource {
    override suspend fun reportDiscussion(
        discussionId: Long,
        request: ReportDiscussionRequest,
    ): Result<Unit> = safeApiCall {
        service.reportDiscussion(discussionId = discussionId, request = request)
    }

    override suspend fun reportComment(
        discussionId: Long,
        commentId: Long,
        request: ReportCommentRequest,
    ): Result<Unit> = safeApiCall {
        service.reportComment(discussionId = discussionId, commentId = commentId, request = request)
    }
}
