package com.on.network.datasourceimpl

import com.on.network.common.safeApiCall
import com.on.network.datasource.DiscussionSummaryDatasource
import com.on.network.dto.request.DiscussionSummaryRequest
import com.on.network.dto.response.discussionsummary.DiscussionSummaryResponse
import com.on.network.service.DiscussionService

class DiscussionSummaryRemoteDatasource(
    private val discussionService: DiscussionService
) : DiscussionSummaryDatasource {
    override suspend fun createDiscussionSummary(request: DiscussionSummaryRequest): Result<DiscussionSummaryResponse> =
        safeApiCall { discussionService.postDiscussionSummary(request) }
}
