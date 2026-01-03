package com.on.dialog.data.service

import com.on.dialog.data.dto.request.OfflineDiscussionEditRequest
import com.on.dialog.data.dto.request.OnlineDiscussionEditRequest
import com.on.dialog.data.dto.response.common.DataResponse
import com.on.dialog.data.dto.response.discussioncreate.DiscussionCreateResponse

interface DiscussionUpdateService {
    suspend fun updateOfflineDiscussion(
        id: Long,
        request: OfflineDiscussionEditRequest,
    ): Result<DataResponse<Unit>>

    suspend fun updateOnlineDiscussion(
        id: Long,
        request: OnlineDiscussionEditRequest,
    ): Result<DataResponse<Unit>>
}
