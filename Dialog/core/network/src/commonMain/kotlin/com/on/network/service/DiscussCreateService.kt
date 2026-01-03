package com.on.network.service

import com.on.network.dto.request.CreateOfflineDiscussionRequest
import com.on.network.dto.request.CreateOnlineDiscussionRequest
import com.on.network.dto.response.common.DataResponse
import com.on.network.dto.response.discussioncreate.DiscussionCreateResponse
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST

interface DiscussCreateService {
    @POST("api/discussions/offline")
    suspend fun postOfflineDiscussion(
        @Body request: CreateOfflineDiscussionRequest,
    ): Response<DataResponse<DiscussionCreateResponse>>

    @POST("api/discussions/online")
    suspend fun postOnlineDiscussion(
        @Body request: CreateOnlineDiscussionRequest,
    ): Response<DataResponse<DiscussionCreateResponse>>
}
