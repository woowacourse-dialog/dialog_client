package com.on.model

data class DiscussionCursorPage(
    val discussionPreviewResponse: List<Content>,
    val hasNext: Boolean,
    val nextCursor: String,
)
