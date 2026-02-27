package com.on.dialog.domain.repository

import com.on.dialog.model.discussion.cursorpage.ScrapCatalogCursorPage
import com.on.dialog.model.scrap.ScrapStatus

interface ScrapRepository {
    suspend fun getScraps(lastCursorId: Long, size: Int): Result<ScrapCatalogCursorPage>
    suspend fun postScrap(discussionId: Long): Result<Unit>

    suspend fun deleteScrap(discussionId: Long): Result<Unit>

    suspend fun getScrapStatus(discussionId: Long): Result<ScrapStatus>
}
