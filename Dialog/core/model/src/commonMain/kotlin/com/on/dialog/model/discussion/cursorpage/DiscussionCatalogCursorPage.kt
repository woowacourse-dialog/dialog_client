package com.on.dialog.model.discussion.cursorpage

import com.on.dialog.model.discussion.catalog.DiscussionCatalog

data class DiscussionCatalogCursorPage(
    val discussionCatalog: List<DiscussionCatalog>,
    val hasNext: Boolean,
    val nextCursor: String?,
)
