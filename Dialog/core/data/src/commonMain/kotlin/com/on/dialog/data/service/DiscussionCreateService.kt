package com.on.dialog.data.service

import com.on.dialog.data.dto.request.CreateOfflineDiscussionRequest
import com.on.dialog.data.dto.request.CreateOnlineDiscussionRequest
import com.on.dialog.data.dto.response.common.DataResponse
import com.on.dialog.data.dto.response.discussioncreate.DiscussionCreateResponse

interface DiscussionCreateService {
    suspend fun createOfflineDiscussion(request: CreateOfflineDiscussionRequest): Result<DataResponse<DiscussionCreateResponse>>

    suspend fun createOnlineDiscussion(request: CreateOnlineDiscussionRequest): Result<DataResponse<DiscussionCreateResponse>>
}
