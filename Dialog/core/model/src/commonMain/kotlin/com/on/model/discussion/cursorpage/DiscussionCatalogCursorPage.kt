package com.on.model.discussion.cursorpage

import com.on.model.discussion.catalog.DiscussionCatalog

data class DiscussionCatalogCursorPage(
    val discussionCatalogResponse: List<DiscussionCatalog>,
    val hasNext: Boolean,
    val nextCursor: String,
)
