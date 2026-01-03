package com.on.network.datasourceimpl

import com.on.network.datasource.DiscussionUpdateDatasource
import com.on.network.dto.request.OfflineDiscussionEditRequest
import com.on.network.dto.request.OnlineDiscussionEditRequest

class RemoteDiscussionUpdateDatasource : DiscussionUpdateDatasource {
    override suspend fun updateOfflineDiscussion(
        id: Long,
        request: OfflineDiscussionEditRequest,
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateOnlineDiscussion(
        id: Long,
        request: OnlineDiscussionEditRequest,
    ): Result<Unit> {
        TODO("Not yet implemented")
    }
}
