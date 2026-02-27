package com.on.dialog.data.repository

import com.on.dialog.domain.repository.ScrapRepository
import com.on.dialog.model.discussion.cursorpage.ScrapCatalogCursorPage
import com.on.dialog.network.datasource.ScrapDatasource

class ScrapDefaultRepository(
    private val scrapDatasource: ScrapDatasource,
) : ScrapRepository {
    override suspend fun getScraps(lastCursorId: Long, size: Int): Result<ScrapCatalogCursorPage> =
        scrapDatasource.getScraps(lastCursorId, size).mapCatching {
            it.toDomain()
        }
}
