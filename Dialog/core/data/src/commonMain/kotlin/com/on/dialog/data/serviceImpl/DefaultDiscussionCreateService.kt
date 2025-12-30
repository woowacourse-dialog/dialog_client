package com.on.dialog.data.serviceImpl

import com.on.dialog.data.dto.request.CreateOfflineDiscussionRequest
import com.on.dialog.data.dto.request.CreateOnlineDiscussionRequest
import com.on.dialog.data.dto.response.DiscussionCreateResponse
import com.on.dialog.data.service.DiscussionCreateService
import io.ktor.client.HttpClient

class DefaultDiscussionCreateService(
    private val client: HttpClient,
) : DiscussionCreateService {
    override suspend fun createOfflineDiscussion(request: CreateOfflineDiscussionRequest): Result<DiscussionCreateResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun createOnlineDiscussion(request: CreateOnlineDiscussionRequest): Result<DiscussionCreateResponse> {
        TODO("Not yet implemented")
    }
}
