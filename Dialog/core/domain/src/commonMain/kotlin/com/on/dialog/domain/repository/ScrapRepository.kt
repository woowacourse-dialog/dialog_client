package com.on.dialog.domain.repository

import com.on.dialog.model.discussion.cursorpage.ScrapCatalogCursorPage

interface ScrapRepository {
    suspend fun getScraps(lastCursorId: Long, size: Int): Result<ScrapCatalogCursorPage>
}