package com.on.dialog.data.serviceImpl

import com.on.dialog.data.dto.query.DiscussionQuery
import com.on.dialog.data.dto.response.common.DataResponse
import com.on.dialog.data.dto.response.discussiondetail.DiscussionDetailResponse
import com.on.dialog.data.dto.response.discussionlookup.DiscussionCursorPageResponse
import com.on.dialog.data.service.DiscussionService
import com.on.dialog.domain.Discussion
import io.ktor.client.HttpClient

class DefaultDiscussionService(
    private val client: HttpClient,
) : DiscussionService {
    override suspend fun getDiscussion(id: Long): Result<DataResponse<DiscussionDetailResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDiscussions(): Result<DataResponse<DiscussionCursorPageResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyDiscussions(): Result<DataResponse<DiscussionCursorPageResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchDiscussions(
        searchBy: String,
        keyword: String,
        query: DiscussionQuery,
    ): Result<DataResponse<DiscussionCursorPageResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDiscussion(id: Long): Result<DataResponse<Unit>> {
        TODO("Not yet implemented")
    }
}
