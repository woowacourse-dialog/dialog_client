package com.on.dialog.network.datasourceimpl

import com.on.dialog.network.common.safeApiCall
import com.on.dialog.network.datasource.ScrapDatasource
import com.on.dialog.network.dto.scrap.ScrapCursorPageResponse
import com.on.dialog.network.dto.scrap.ScrapStatusResponse
import com.on.dialog.network.service.ScrapService

internal class ScrapRemoteDatasource(
    private val scrapService: ScrapService,
) : ScrapDatasource {
    override suspend fun getScraps(
        lastCursorId: Long?,
        size: Int,
    ): Result<ScrapCursorPageResponse> =
        safeApiCall { scrapService.getScraps(lastCursorId, size) }

    override suspend fun postScrap(id: Long): Result<Unit> =
        safeApiCall { scrapService.postScrap(id = id) }

    override suspend fun deleteScrap(id: Long): Result<Unit> =
        safeApiCall { scrapService.deleteScrap(id = id) }

    override suspend fun getScrapStatus(id: Long): Result<ScrapStatusResponse> =
        safeApiCall { scrapService.getScrapStatus(id = id) }
}
