package com.on.dialog.model.discussion.criteria

import com.on.dialog.model.common.Track
import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.content.DiscussionType

data class DiscussionCriteria(
    val tracks: List<Track> = emptyList(),
    val statuses: List<DiscussionStatus> = emptyList(),
    val discussionTypes: List<DiscussionType> = emptyList(),
)
