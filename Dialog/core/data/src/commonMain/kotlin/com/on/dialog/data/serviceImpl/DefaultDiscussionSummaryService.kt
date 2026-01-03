package com.on.dialog.data.serviceImpl

import com.on.dialog.data.dto.request.DiscussionSummaryRequest
import com.on.dialog.data.dto.response.common.DataResponse
import com.on.dialog.data.dto.response.discussionsummary.DiscussionSummaryResponse
import com.on.dialog.data.service.DiscussionSummaryService
import io.ktor.client.HttpClient

class DefaultDiscussionSummaryService(
    private val client: HttpClient,
) : DiscussionSummaryService {
    override suspend fun createDiscussionSummary(request: DiscussionSummaryRequest): Result<DataResponse<DiscussionSummaryResponse>> {
        TODO("Not yet implemented")
    }
}
