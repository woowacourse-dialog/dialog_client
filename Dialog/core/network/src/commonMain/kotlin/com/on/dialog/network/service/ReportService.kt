package com.on.dialog.network.service

import com.on.dialog.network.dto.common.DataResponse
import com.on.dialog.network.dto.report.ReportCommentRequest
import com.on.dialog.network.dto.report.ReportDiscussionRequest
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface ReportService {
    @POST("api/discussions/{discussionId}/reports")
    suspend fun reportDiscussion(
        @Path("discussionId") discussionId: Long,
        @Body request: ReportDiscussionRequest,
    ): DataResponse<Unit>

    @POST("api/discussions/{discussionId}/comments/{commentId}/reports")
    suspend fun reportComment(
        @Path("discussionId") discussionId: Long,
        @Path("commentId") commentId: Long,
        @Body request: ReportCommentRequest,
    ): DataResponse<Unit>
}
