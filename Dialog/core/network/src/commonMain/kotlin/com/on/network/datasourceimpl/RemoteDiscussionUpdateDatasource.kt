package com.on.network.datasourceimpl

import com.on.network.common.safeApiCall
import com.on.network.datasource.DiscussionUpdateDatasource
import com.on.network.dto.request.OfflineDiscussionEditRequest
import com.on.network.dto.request.OnlineDiscussionEditRequest
import com.on.network.service.DiscussionService

class RemoteDiscussionUpdateDatasource(
    private val discussionService: DiscussionService
) : DiscussionUpdateDatasource {
    override suspend fun updateOfflineDiscussion(
        id: Long,
        request: OfflineDiscussionEditRequest,
    ): Result<Unit> =
        safeApiCall { discussionService.updateOfflineDiscussion(id, request) }

    override suspend fun updateOnlineDiscussion(
        id: Long,
        request: OnlineDiscussionEditRequest,
    ): Result<Unit> =
        safeApiCall { discussionService.updateOnlineDiscussion(id, request) }
}
