package com.on.dialog.network.service

import com.on.dialog.network.dto.common.DataResponse
import com.on.dialog.network.dto.like.LikeStatusResponse
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface LikeService {
    @POST("api/discussions/{discussionsId}/likes")
    suspend fun postLike(
        @Path("discussionsId") id: Long,
    ): DataResponse<Unit>

    @DELETE("api/discussions/{discussionsId}/likes")
    suspend fun deleteLike(
        @Path("discussionsId") id: Long,
    ): DataResponse<Unit>

    @GET("api/discussions/{discussionsId}/likes/status")
    suspend fun getLike(
        @Path("discussionsId") id: Long,
    ): DataResponse<LikeStatusResponse>
}
