package com.on.dialog.domain

data class DiscussionCursorPage(
    val discussionPreviewResponse: List<Content>,
    val hasNext: Boolean,
    val nextCursor: String,
)
