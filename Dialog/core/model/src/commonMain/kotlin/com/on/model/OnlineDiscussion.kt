package com.on.model

import kotlinx.datetime.LocalDate

data class OnlineDiscussion(
    override val content: Content,
    override val summary: String?,
    val endDate: LocalDate,
) : Discussion
