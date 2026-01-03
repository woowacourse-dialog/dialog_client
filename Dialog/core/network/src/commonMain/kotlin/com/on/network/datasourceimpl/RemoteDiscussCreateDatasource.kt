package com.on.network.datasourceimpl

import com.on.network.datasource.DiscussionCreateDatasource
import com.on.network.dto.request.CreateOfflineDiscussionRequest
import com.on.network.dto.request.CreateOnlineDiscussionRequest
import com.on.network.dto.response.discussioncreate.DiscussionCreateResponse

class RemoteDiscussCreateDatasource : DiscussionCreateDatasource {
    override suspend fun createOfflineDiscussion(request: CreateOfflineDiscussionRequest): Result<DiscussionCreateResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun createOnlineDiscussion(request: CreateOnlineDiscussionRequest): Result<DiscussionCreateResponse> {
        TODO("Not yet implemented")
    }
}
