package com.on.network.datasource

import com.on.network.dto.request.DiscussionSummaryRequest
import com.on.network.dto.response.discussionsummary.DiscussionSummaryResponse

interface DiscussionSummaryDatasource {
    suspend fun createDiscussionSummary(
        request: DiscussionSummaryRequest,
    ): Result<DiscussionSummaryResponse>
}
