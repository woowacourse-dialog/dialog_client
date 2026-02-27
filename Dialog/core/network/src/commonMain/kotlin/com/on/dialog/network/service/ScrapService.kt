package com.on.dialog.network.service

import com.on.dialog.network.dto.common.DataResponse
import com.on.dialog.network.dto.scrap.ScrapCursorPageResponse
import com.on.dialog.network.dto.scrap.ScrapStatusResponse
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query

interface ScrapService {
    @GET("api/scraps/me")
    suspend fun getScraps(
        @Query lastCursorId: Long?,
        @Query size: Int,
    ): DataResponse<ScrapCursorPageResponse>

    @POST("api/discussions/{discussionId}/scraps")
    suspend fun postScrap(
        @Path("discussionId") id: Long,
    ): DataResponse<Unit>

    @DELETE("api/discussions/{discussionId}/scraps")
    suspend fun deleteScrap(
        @Path("discussionId") id: Long,
    ): DataResponse<Unit>

    @GET("api/discussions/{discussionId}/scraps/status")
    suspend fun getScrapStatus(
        @Path("discussionId") id: Long,
    ): DataResponse<ScrapStatusResponse>
}
