package com.on.model.discussion

import com.on.model.discussion.Content
import kotlinx.datetime.LocalDate

data class OnlineDiscussion(
    override val content: Content,
    override val summary: String?,
    val endDate: LocalDate,
) : Discussion