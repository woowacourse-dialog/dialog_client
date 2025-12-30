package com.on.dialog.data.serviceImpl

import com.on.dialog.data.dto.query.DiscussionQuery
import com.on.dialog.data.dto.response.DiscussionCreateResponse
import com.on.dialog.data.service.DiscussionService
import com.on.dialog.domain.Discussion
import io.ktor.client.HttpClient

class DefaultDiscussionService(
    private val client: HttpClient,
) : DiscussionService {
    override suspend fun getDiscussion(id: Long): Result<Discussion> {
        TODO("Not yet implemented")
    }

    override suspend fun getDiscussions(): Result<List<Discussion>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyDiscussions(): Result<List<Discussion>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchDiscussions(
        searchBy: String,
        keyword: String,
        query: DiscussionQuery,
    ): Result<List<Discussion>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDiscussion(id: Long): Result<DiscussionCreateResponse> {
        TODO("Not yet implemented")
    }
}
