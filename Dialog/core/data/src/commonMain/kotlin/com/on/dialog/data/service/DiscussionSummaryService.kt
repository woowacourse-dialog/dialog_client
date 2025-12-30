package com.on.dialog.data.service

import com.on.dialog.data.dto.request.DiscussionSummaryRequest
import com.on.dialog.data.dto.response.DiscussionSummaryResponse

interface DiscussionSummaryService {
    suspend fun createDiscussionSummary(
        request: DiscussionSummaryRequest,
    ): Result<DiscussionSummaryResponse>
}
