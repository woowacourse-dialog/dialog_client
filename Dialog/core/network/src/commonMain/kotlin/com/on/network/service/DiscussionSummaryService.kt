package com.on.network.service

import com.on.dialog.data.dto.request.DiscussionSummaryRequest
import com.on.dialog.data.dto.response.common.DataResponse
import com.on.dialog.data.dto.response.discussionsummary.DiscussionSummaryResponse
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST

interface DiscussionSummaryService {
    @POST("api/discussions/summary")
    suspend fun postDiscussionSummary(
        @Body request: DiscussionSummaryRequest,
    ): Response<DataResponse<DiscussionSummaryResponse>>
}
