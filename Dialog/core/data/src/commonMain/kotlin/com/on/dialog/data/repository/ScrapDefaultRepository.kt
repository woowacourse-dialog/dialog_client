package com.on.dialog.data.repository

import com.on.dialog.domain.repository.ScrapRepository
import com.on.dialog.model.discussion.cursorpage.ScrapCatalogCursorPage
import com.on.dialog.model.discussion.scrap.ScrapCatalog
import com.on.dialog.model.scrap.ScrapStatus
import com.on.dialog.network.datasource.ScrapDatasource
import com.on.dialog.network.dto.scrap.ScrapSuccessResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class ScrapDefaultRepository(
    private val scrapDatasource: ScrapDatasource,
) : ScrapRepository {
    private val _scrapCatalogs = MutableStateFlow<ImmutableList<ScrapCatalog>>(persistentListOf())
    override val scrapCatalogs: StateFlow<ImmutableList<ScrapCatalog>> =
        _scrapCatalogs.asStateFlow()

    override suspend fun getScraps(lastCursorId: Long?, size: Int): Result<ScrapCatalogCursorPage> =
        scrapDatasource
            .getScraps(lastCursorId, size)
            .mapCatching { it.toDomain() }
            .onSuccess { page: ScrapCatalogCursorPage ->
                _scrapCatalogs.update { current: ImmutableList<ScrapCatalog> ->
                    if (lastCursorId == null) {
                        page.scrapCatalog
                            .distinctBy { it.catalogContent.id }
                            .toImmutableList()
                    } else {
                        (current + page.scrapCatalog)
                            .distinctBy { it.catalogContent.id }
                            .toImmutableList()
                    }
                }
            }

    override suspend fun postScrap(discussionId: Long): Result<Unit> =
        scrapDatasource
            .postScrap(id = discussionId)
            .mapCatching { response: ScrapSuccessResponse ->
                val scrapCatalog: ScrapCatalog = response.toDomain()
                _scrapCatalogs.update { current ->
                    (persistentListOf(scrapCatalog) + current)
                        .distinctBy { it.catalogContent.id }
                        .toImmutableList()
                }
            }

    override suspend fun deleteScrap(discussionId: Long): Result<Unit> =
        scrapDatasource.deleteScrap(id = discussionId).onSuccess {
            _scrapCatalogs.update { current ->
                current.filterNot { it.catalogContent.id == discussionId }.toImmutableList()
            }
        }

    override suspend fun getScrapStatus(discussionId: Long): Result<ScrapStatus> =
        scrapDatasource
            .getScrapStatus(id = discussionId)
            .map { it.toDomain() }
}
