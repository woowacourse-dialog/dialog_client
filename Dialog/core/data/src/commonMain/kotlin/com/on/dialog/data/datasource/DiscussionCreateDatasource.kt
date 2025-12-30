package com.on.dialog.data.datasource

import com.on.dialog.data.dto.request.CreateOfflineDiscussionRequest
import com.on.dialog.data.dto.request.CreateOnlineDiscussionRequest
import com.on.dialog.data.dto.response.DiscussionCreateResponse
import com.on.dialog.domain.Discussion
import com.on.dialog.domain.OfflineDiscussion
import com.on.dialog.domain.OnlineDiscussion

interface DiscussionCreateDatasource {
    suspend fun createOfflineDiscussion(request: CreateOfflineDiscussionRequest): Result<DiscussionCreateResponse>

    suspend fun createOnlineDiscussion(request: CreateOnlineDiscussionRequest): Result<DiscussionCreateResponse>
}
