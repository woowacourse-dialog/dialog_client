package com.on.dialog.network.service

import com.on.dialog.network.dto.common.DataResponse
import com.on.dialog.network.dto.participation.ParticipationStatusResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface ParticipantService {
    @POST("api/discussions/{discussionId}/participants")
    suspend fun postParticipation(
        @Path("discussionId") id: Long,
    ): DataResponse<Unit>

    @GET("api/discussions/{discussionId}/participants/status")
    suspend fun getParticipationStatus(
        @Path("discussionId") id: Long,
    ): DataResponse<ParticipationStatusResponse>
}
