package com.on.network.datasourceimpl

import com.on.network.common.safeApiCall
import com.on.network.datasource.DiscussionSummaryDatasource
import com.on.network.dto.request.DiscussionSummaryRequest
import com.on.network.dto.response.discussionsummary.DiscussionSummaryResponse
import com.on.network.service.DiscussionSummaryService

class RemoteDiscussionSummaryDatasource(
    private val discussionSummaryService: DiscussionSummaryService
) : DiscussionSummaryDatasource {
    override suspend fun createDiscussionSummary(request: DiscussionSummaryRequest): Result<DiscussionSummaryResponse> =
        safeApiCall { discussionSummaryService.postDiscussionSummary(request) }
}
