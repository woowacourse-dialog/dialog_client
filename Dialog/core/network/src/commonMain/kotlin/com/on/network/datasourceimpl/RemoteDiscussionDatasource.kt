package com.on.network.datasourceimpl

import com.on.network.datasource.DiscussionDatasource
import com.on.network.dto.query.DiscussionQuery
import com.on.network.dto.response.discussiondetail.DiscussionDetailResponse
import com.on.network.dto.response.discussionlookup.DiscussionCursorPageResponse

class RemoteDiscussionDatasource : DiscussionDatasource {
    override suspend fun getDiscussionDetail(id: Long): Result<DiscussionDetailResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getDiscussions(): Result<DiscussionCursorPageResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyDiscussions(): Result<DiscussionCursorPageResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun searchDiscussions(
        searchBy: String,
        keyword: String,
        query: DiscussionQuery,
    ): Result<DiscussionCursorPageResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDiscussion(id: Long): Result<Unit> {
        TODO("Not yet implemented")
    }
}
