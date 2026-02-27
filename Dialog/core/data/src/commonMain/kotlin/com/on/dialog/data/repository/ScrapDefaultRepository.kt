package com.on.dialog.data.repository

import com.on.dialog.domain.repository.ScrapRepository
import com.on.dialog.model.scrap.ScrapStatus
import com.on.dialog.network.datasource.ScrapDatasource

internal class ScrapDefaultRepository(
    private val scrapDatasource: ScrapDatasource,
) : ScrapRepository {
    override suspend fun postScrap(discussionId: Long): Result<Unit> =
        scrapDatasource.postScrap(id = discussionId)

    override suspend fun deleteScrap(discussionId: Long): Result<Unit> =
        scrapDatasource.deleteScrap(id = discussionId)

    override suspend fun getScrapStatus(discussionId: Long): Result<ScrapStatus> =
        scrapDatasource
            .getScrapStatus(id = discussionId)
            .map { it.toDomain() }
}
