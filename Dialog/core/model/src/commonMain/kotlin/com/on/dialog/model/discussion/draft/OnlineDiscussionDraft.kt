package com.on.dialog.model.discussion.draft

import kotlinx.datetime.LocalDate

data class OnlineDiscussionDraft(
    override val title: String,
    override val content: String,
    override val category: String,
    val endDate: LocalDate,
) : DiscussionDraft
