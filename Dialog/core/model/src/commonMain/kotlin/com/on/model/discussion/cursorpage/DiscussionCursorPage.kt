package com.on.model.discussion.cursorpage

import com.on.model.discussion.Content

data class DiscussionCursorPage(
    val discussionPreviewResponse: List<Content>,
    val hasNext: Boolean,
    val nextCursor: String,
)
