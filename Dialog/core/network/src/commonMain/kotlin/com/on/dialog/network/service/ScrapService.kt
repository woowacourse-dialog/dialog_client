package com.on.dialog.network.service

import com.on.dialog.network.dto.common.DataResponse
import com.on.dialog.network.dto.scrap.ScrapCursorPageResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

interface ScrapService {
    @GET("api/scraps/me")
    fun getScraps(
        @Query lastCursorId: Long,
        @Query size: Int,
    ): DataResponse<ScrapCursorPageResponse>
}
