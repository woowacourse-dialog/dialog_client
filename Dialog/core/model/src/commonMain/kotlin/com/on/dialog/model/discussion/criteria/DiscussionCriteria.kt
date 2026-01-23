package com.on.dialog.model.discussion.criteria

import com.on.dialog.model.discussion.content.DiscussionCategory
import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.content.DiscussionType

data class DiscussionCriteria(
    val categories: List<com.on.dialog.model.discussion.content.DiscussionCategory>?,
    val statuses: List<com.on.dialog.model.discussion.content.DiscussionStatus>?,
    val discussionTypes: List<com.on.dialog.model.discussion.content.DiscussionType>?,
)
