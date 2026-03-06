package com.on.dialog.network.datasource

import com.on.dialog.network.dto.scrap.ScrapCursorPageResponse
import com.on.dialog.network.dto.scrap.ScrapStatusResponse

interface ScrapDatasource {
    suspend fun getScraps(lastCursorId: Long?, size: Int): Result<ScrapCursorPageResponse>

    suspend fun postScrap(id: Long): Result<ScrapCursorPageResponse.ContentDto>

    suspend fun deleteScrap(id: Long): Result<Unit>

    suspend fun getScrapStatus(id: Long): Result<ScrapStatusResponse>
}
