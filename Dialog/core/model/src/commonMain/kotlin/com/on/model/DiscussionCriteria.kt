package com.on.model

data class DiscussionCriteria(
    val categories: List<DiscussionCategory>?,
    val statuses: List<DiscussionStatus>?,
    val discussionTypes: List<DiscussionType>?,
)
