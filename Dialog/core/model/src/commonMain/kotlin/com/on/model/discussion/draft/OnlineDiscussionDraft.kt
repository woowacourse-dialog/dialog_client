package com.on.model.discussion.draft

import kotlinx.datetime.LocalDate

data class OnlineDiscussionDraft(
    val title: String,
    val content: String,
    val endDate: LocalDate,
    val category: String,
)
