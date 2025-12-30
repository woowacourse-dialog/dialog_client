package com.on.dialog.data.datasource

import com.on.dialog.data.dto.request.OfflineDiscussionEditRequest
import com.on.dialog.data.dto.request.OnlineDiscussionEditRequest
import com.on.dialog.data.dto.response.DiscussionCreateResponse
import com.on.dialog.domain.Discussion
import com.on.dialog.domain.OfflineDiscussion
import com.on.dialog.domain.OnlineDiscussion

interface DiscussionUpdateDatasource {
    suspend fun updateOfflineDiscussion(
        id: Long,
        request: OfflineDiscussionEditRequest,
    ): Result<DiscussionCreateResponse>

    suspend fun updateOnlineDiscussion(
        id: Long,
        request: OnlineDiscussionEditRequest,
    ): Result<DiscussionCreateResponse>
}
