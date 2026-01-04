package com.on.network.datasourceimpl

import com.on.network.common.safeApiCall
import com.on.network.datasource.DiscussionCreateDatasource
import com.on.network.dto.request.CreateOfflineDiscussionRequest
import com.on.network.dto.request.CreateOnlineDiscussionRequest
import com.on.network.dto.response.discussioncreate.DiscussionCreateResponse
import com.on.network.service.DiscussionService

class RemoteDiscussCreateDatasource(
    private val discussionService: DiscussionService
) : DiscussionCreateDatasource {
    override suspend fun createOfflineDiscussion(request: CreateOfflineDiscussionRequest): Result<DiscussionCreateResponse> =
        safeApiCall { discussionService.postOfflineDiscussion(request) }


    override suspend fun createOnlineDiscussion(request: CreateOnlineDiscussionRequest): Result<DiscussionCreateResponse> =
        safeApiCall { discussionService.postOnlineDiscussion(request) }
}
