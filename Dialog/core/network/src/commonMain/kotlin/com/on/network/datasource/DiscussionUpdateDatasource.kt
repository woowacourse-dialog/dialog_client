package com.on.network.datasource

import com.on.network.dto.request.OfflineDiscussionEditRequest
import com.on.network.dto.request.OnlineDiscussionEditRequest


interface DiscussionUpdateDatasource {
    suspend fun updateOfflineDiscussion(
        id: Long,
        request: OfflineDiscussionEditRequest,
    ): Result<Unit>

    suspend fun updateOnlineDiscussion(
        id: Long,
        request: OnlineDiscussionEditRequest,
    ): Result<Unit>
}
