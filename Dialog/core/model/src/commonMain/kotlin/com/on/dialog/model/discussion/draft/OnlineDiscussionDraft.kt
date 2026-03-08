package com.on.dialog.model.discussion.draft

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class OnlineDiscussionDraft(
    override val title: String,
    override val content: String,
    override val category: String,
    val endDate: LocalDate,
) : DiscussionDraft {

    override fun validate(today: LocalDateTime): List<DraftValidationError> = buildList {
        if (endDate <= today.date)
            add(DraftValidationError.Online.EndDateNotAfterToday)
    }
}