package com.on.dialog.model.discussion.criteria

import com.on.dialog.model.discussion.content.DiscussionCategory
import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.content.DiscussionType

data class DiscussionCriteria(
    val categories: List<DiscussionCategory>?,
    val statuses: List<DiscussionStatus>?,
    val discussionTypes: List<DiscussionType>?,
)
