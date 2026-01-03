package com.on.dialog.data.service

import com.on.dialog.data.dto.query.DiscussionQuery
import com.on.dialog.data.dto.response.common.DataResponse
import com.on.dialog.data.dto.response.discussiondetail.DiscussionDetailResponse
import com.on.dialog.data.dto.response.discussionlookup.DiscussionCursorPageResponse
import com.on.dialog.domain.Discussion

interface DiscussionService {
    suspend fun getDiscussion(id: Long): Result<DataResponse<DiscussionDetailResponse>>

    suspend fun getDiscussions(): Result<DataResponse<DiscussionCursorPageResponse>>

    suspend fun getMyDiscussions(): Result<DataResponse<DiscussionCursorPageResponse>>

    suspend fun searchDiscussions(
        searchBy: String,
        keyword: String,
        query: DiscussionQuery,
    ): Result<DataResponse<DiscussionCursorPageResponse>>

    suspend fun deleteDiscussion(id: Long): Result<DataResponse<Unit>>
}
