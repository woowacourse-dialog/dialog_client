package com.on.dialog.domain.repository

import com.on.dialog.model.discussion.cursorpage.ScrapCatalogCursorPage

interface ScrapRepository {
    suspend fun getScraps(): Result<ScrapCatalogCursorPage>
}