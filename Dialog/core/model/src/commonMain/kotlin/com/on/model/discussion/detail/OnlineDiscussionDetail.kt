package com.on.model.discussion.detail

import com.on.model.discussion.content.DetailContent
import kotlinx.datetime.LocalDate

data class OnlineDiscussionDetail(
    override val detailContent: DetailContent,
    override val summary: String?,
    val endDate: LocalDate,
) : DiscussionDetail
