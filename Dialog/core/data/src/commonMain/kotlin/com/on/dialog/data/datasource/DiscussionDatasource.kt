package com.on.dialog.data.datasource

import com.on.dialog.data.dto.query.DiscussionQuery
import com.on.dialog.data.dto.response.discussiondetail.DiscussionDetailResponse
import com.on.dialog.data.dto.response.discussionlookup.DiscussionCursorPageResponse

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
