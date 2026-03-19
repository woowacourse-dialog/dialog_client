package com.on.dialog.model.discussion.cursorpage

import com.on.dialog.model.discussion.scrap.ScrapCatalog

data class ScrapCatalogCursorPage(
    val scrapCatalog: List<ScrapCatalog>,
    val nextCursorId: Long?,
    val hasNext: Boolean,
    val size: Int,
)
