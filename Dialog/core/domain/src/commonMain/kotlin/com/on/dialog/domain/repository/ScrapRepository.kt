package com.on.dialog.domain.repository

import com.on.dialog.model.scrap.ScrapStatus

interface ScrapRepository {
    suspend fun postScrap(discussionId: Long): Result<Unit>

    suspend fun deleteScrap(discussionId: Long): Result<Unit>

    suspend fun getScrapStatus(discussionId: Long): Result<ScrapStatus>
}
