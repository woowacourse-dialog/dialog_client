package com.on.network.datasource

import com.on.dialog.data.datasource.DiscussionCreateDatasource
import com.on.dialog.data.dto.request.CreateOfflineDiscussionRequest
import com.on.dialog.data.dto.request.CreateOnlineDiscussionRequest
import com.on.dialog.data.dto.response.discussioncreate.DiscussionCreateResponse

class RemoteDiscussCreateDatasource : DiscussionCreateDatasource {
    override suspend fun createOfflineDiscussion(request: CreateOfflineDiscussionRequest): Result<DiscussionCreateResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun createOnlineDiscussion(request: CreateOnlineDiscussionRequest): Result<DiscussionCreateResponse> {
        TODO("Not yet implemented")
    }
}
