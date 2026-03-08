package com.on.dialog.model.discussion.draft

sealed interface DiscussionDraft {
    val title: String
    val content: String
    val category: String
}