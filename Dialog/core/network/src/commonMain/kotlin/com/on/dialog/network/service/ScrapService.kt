package com.on.dialog.network.service

import com.on.dialog.network.dto.common.DataResponse
import com.on.dialog.network.dto.scrap.ScrapCursorPageResponse
import de.jensklingenberg.ktorfit.http.GET

interface ScrapService {
    @GET("api/scraps/me")
    fun getScraps(): DataResponse<ScrapCursorPageResponse>
}
