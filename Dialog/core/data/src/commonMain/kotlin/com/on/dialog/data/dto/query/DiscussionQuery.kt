package com.on.dialog.data.dto.query

import com.on.dialog.domain.DiscussionCategory
import com.on.dialog.domain.DiscussionStatus
import com.on.dialog.domain.DiscussionType

data class DiscussionQuery(
    val categories: List<DiscussionCategory>?,
    val statuses: List<DiscussionStatus>?,
    val discussionTypes: List<DiscussionType>?,
    val cursor: String,
    val size: Int,
)
