package com.on.network.datasource

import com.on.network.dto.query.DiscussionQuery
import com.on.network.dto.response.discussiondetail.DiscussionDetailResponse
import com.on.network.dto.response.discussionlookup.DiscussionCursorPageResponse

interface DiscussionDatasource {
    suspend fun getDiscussionDetail(id: Long): Result<DiscussionDetailResponse>

    suspend fun getDiscussions(): Result<DiscussionCursorPageResponse>

    suspend fun getMyDiscussions(): Result<DiscussionCursorPageResponse>

    suspend fun searchDiscussions(
        searchBy: String,
        keyword: String,
        query: DiscussionQuery,
    ): Result<DiscussionCursorPageResponse>

    suspend fun deleteDiscussion(id: Long): Result<Unit>
}
