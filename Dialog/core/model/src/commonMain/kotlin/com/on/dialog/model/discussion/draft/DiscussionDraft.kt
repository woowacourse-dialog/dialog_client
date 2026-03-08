package com.on.dialog.model.discussion.draft

import kotlinx.datetime.LocalDate

sealed interface DiscussionDraft {
    val title: String
    val content: String
    val category: String

    fun validate(time: LocalDate): List<DraftValidationError>
}