package com.on.dialog.data.repository

import com.on.dialog.domain.repository.ScrapRepository
import com.on.dialog.model.discussion.cursorpage.ScrapCatalogCursorPage
import com.on.dialog.network.datasource.ScrapDatasource

class ScrapDefaultRepository(
    private val scrapDatasource: ScrapDatasource,
) : ScrapRepository {
    override suspend fun getScraps(): Result<ScrapCatalogCursorPage> =
        scrapDatasource.getScraps().mapCatching {
            it.toDomain()
        }
}
