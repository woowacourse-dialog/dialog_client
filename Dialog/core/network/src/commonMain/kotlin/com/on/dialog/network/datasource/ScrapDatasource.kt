package com.on.dialog.network.datasource

import com.on.dialog.network.dto.scrap.ScrapStatusResponse

interface ScrapDatasource {
    suspend fun postScrap(id: Long): Result<Unit>

    suspend fun deleteScrap(id: Long): Result<Unit>

    suspend fun getScrapStatus(id: Long): Result<ScrapStatusResponse>
}
