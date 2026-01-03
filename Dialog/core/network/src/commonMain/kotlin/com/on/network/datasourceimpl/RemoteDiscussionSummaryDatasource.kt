package com.on.network.datasourceimpl

import com.on.network.datasource.DiscussionSummaryDatasource
import com.on.network.dto.request.DiscussionSummaryRequest
import com.on.network.dto.response.discussionsummary.DiscussionSummaryResponse

class RemoteDiscussionSummaryDatasource : DiscussionSummaryDatasource {
    override suspend fun createDiscussionSummary(request: DiscussionSummaryRequest): Result<DiscussionSummaryResponse> {
        TODO("Not yet implemented")
    }
}
