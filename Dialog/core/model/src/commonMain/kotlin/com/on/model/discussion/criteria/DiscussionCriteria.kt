package com.on.model.discussion.criteria

import com.on.model.discussion.content.DiscussionCategory
import com.on.model.discussion.content.DiscussionStatus
import com.on.model.discussion.content.DiscussionType

data class DiscussionCriteria(
    val categories: List<DiscussionCategory>?,
    val statuses: List<DiscussionStatus>?,
    val discussionTypes: List<DiscussionType>?,
)