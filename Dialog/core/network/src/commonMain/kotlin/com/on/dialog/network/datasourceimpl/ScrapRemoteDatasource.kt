package com.on.dialog.network.datasourceimpl

import com.on.dialog.network.common.safeApiCall
import com.on.dialog.network.datasource.ScrapDatasource
import com.on.dialog.network.dto.scrap.ScrapCursorPageResponse
import com.on.dialog.network.service.ScrapService

class ScrapRemoteDatasource(
    private val scrapService: ScrapService,
) : ScrapDatasource {
    override suspend fun getScraps(lastCursorId: Long, size: Int): Result<ScrapCursorPageResponse> =
        safeApiCall { scrapService.getScraps(lastCursorId, size) }
}
