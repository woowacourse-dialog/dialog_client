package com.on.network.datasource

import com.on.dialog.data.datasource.DiscussionUpdateDatasource
import com.on.dialog.data.dto.request.OfflineDiscussionEditRequest
import com.on.dialog.data.dto.request.OnlineDiscussionEditRequest

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
