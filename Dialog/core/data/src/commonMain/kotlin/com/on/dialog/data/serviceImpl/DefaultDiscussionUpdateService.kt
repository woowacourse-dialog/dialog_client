package com.on.dialog.data.serviceImpl

import com.on.dialog.data.dto.request.OfflineDiscussionEditRequest
import com.on.dialog.data.dto.request.OnlineDiscussionEditRequest
import com.on.dialog.data.dto.response.common.DataResponse
import com.on.dialog.data.dto.response.discussioncreate.DiscussionCreateResponse
import com.on.dialog.data.service.DiscussionUpdateService
import io.ktor.client.HttpClient

class DefaultDiscussionUpdateService(
    private val client: HttpClient,
) : DiscussionUpdateService {
    override suspend fun updateOfflineDiscussion(
        id: Long,
        request: OfflineDiscussionEditRequest,
    ): Result<DataResponse<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateOnlineDiscussion(
        id: Long,
        request: OnlineDiscussionEditRequest,
    ): Result<DataResponse<Unit>> {
        TODO("Not yet implemented")
    }
}
