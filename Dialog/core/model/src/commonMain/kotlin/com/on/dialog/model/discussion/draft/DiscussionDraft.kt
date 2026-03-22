package com.on.dialog.model.discussion.draft

import kotlinx.datetime.LocalDateTime

sealed interface DiscussionDraft {
    val title: String
    val content: String
    val category: String

    fun validate(today: LocalDateTime): List<DraftValidationError>

    companion object {
        const val MAX_TITLE_LENGTH = 50
    }
}
