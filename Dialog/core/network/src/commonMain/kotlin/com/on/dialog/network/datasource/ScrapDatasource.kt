package com.on.dialog.network.datasource

import com.on.dialog.network.dto.scrap.ScrapCursorPageResponse

interface ScrapDatasource {
    suspend fun getScraps(): Result<ScrapCursorPageResponse>
}
