package com.on.dialog.data.datasource

import com.on.dialog.data.dto.request.CreateOfflineDiscussionRequest
import com.on.dialog.data.dto.request.CreateOnlineDiscussionRequest
import com.on.dialog.data.dto.response.discussioncreate.DiscussionCreateResponse

interface DiscussionCreateDatasource {
    suspend fun createOfflineDiscussion(request: CreateOfflineDiscussionRequest): Result<DiscussionCreateResponse>

    suspend fun createOnlineDiscussion(request: CreateOnlineDiscussionRequest): Result<DiscussionCreateResponse>
}
