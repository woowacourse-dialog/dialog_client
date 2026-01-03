package com.on.network.datasource

import com.on.network.dto.request.CreateOfflineDiscussionRequest
import com.on.network.dto.request.CreateOnlineDiscussionRequest
import com.on.network.dto.response.discussioncreate.DiscussionCreateResponse

interface DiscussionCreateDatasource {
    suspend fun createOfflineDiscussion(request: CreateOfflineDiscussionRequest): Result<DiscussionCreateResponse>

    suspend fun createOnlineDiscussion(request: CreateOnlineDiscussionRequest): Result<DiscussionCreateResponse>
}
