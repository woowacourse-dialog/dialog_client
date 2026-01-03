package com.on.network.service

import com.on.dialog.data.dto.request.OfflineDiscussionEditRequest
import com.on.dialog.data.dto.request.OnlineDiscussionEditRequest
import com.on.dialog.data.dto.response.common.DataResponse
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.Path

interface DiscussionUpdateService {
    @PATCH("api/discussions/offline/{id}")
    suspend fun updateOfflineDiscussion(
        @Path("id") id: Long,
        @Body request: OfflineDiscussionEditRequest,
    ): Response<DataResponse<Unit>>

    @PATCH("api/discussions/online/{id}")
    suspend fun updateOnlineDiscussion(
        @Path("id") id: Long,
        @Body request: OnlineDiscussionEditRequest,
    ): Response<DataResponse<Unit>>
}
