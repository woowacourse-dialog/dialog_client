package com.on.dialog.data.repository

import com.on.dialog.domain.repository.ScrapRepository
import com.on.dialog.model.discussion.cursorpage.ScrapCatalogCursorPage
import com.on.dialog.model.discussion.scrap.ScrapCatalog
import com.on.dialog.model.scrap.ScrapStatus
import com.on.dialog.network.datasource.ScrapDatasource
import com.on.dialog.network.dto.scrap.ScrapCursorPageResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class ScrapDefaultRepository(
    private val scrapDatasource: ScrapDatasource,
) : ScrapRepository {
    private val _scrapCatalogs = MutableStateFlow<Map<Long, ScrapCatalog>>(emptyMap())
    override val scrapCatalogs: StateFlow<Map<Long, ScrapCatalog>> = _scrapCatalogs.asStateFlow()

    override suspend fun getScraps(lastCursorId: Long?, size: Int): Result<ScrapCatalogCursorPage> =
        scrapDatasource
            .getScraps(lastCursorId, size)
            .mapCatching { it.toDomain() }

    override suspend fun postScrap(discussionId: Long): Result<Unit> =
        scrapDatasource.postScrap(id = discussionId)
            .mapCatching { contentDto: ScrapCursorPageResponse.ContentDto ->
                when (contentDto) {
                    is ScrapCursorPageResponse.ContentDto.OnlineContentDto -> contentDto.toDomain()
                    is ScrapCursorPageResponse.ContentDto.OfflineContentDto -> contentDto.toDomain()
                }
            }

    override suspend fun deleteScrap(discussionId: Long): Result<Unit> =
        scrapDatasource.deleteScrap(id = discussionId).onSuccess {
            _scrapCatalogs.update { current -> current - discussionId }
        }

    override suspend fun getScrapStatus(discussionId: Long): Result<ScrapStatus> =
        scrapDatasource
            .getScrapStatus(id = discussionId)
            .map { it.toDomain() }
            .onSuccess { status ->
                if (!status.isScraped) {
                    _scrapCatalogs.update { current -> current - discussionId }
                }
            }
}
