package com.on.dialog.data.datasource

import com.on.dialog.data.dto.request.DiscussionSummaryRequest
import com.on.dialog.data.dto.response.DiscussionSummaryResponse

interface DiscussionSummaryDatasource {
    suspend fun createDiscussionSummary(
        request: DiscussionSummaryRequest,
    ): Result<DiscussionSummaryResponse>
}
