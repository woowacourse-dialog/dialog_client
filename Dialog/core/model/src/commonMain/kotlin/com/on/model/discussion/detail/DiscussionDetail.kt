package com.on.model.discussion.detail

import com.on.model.discussion.content.DetailContent

sealed interface DiscussionDetail {
    val detailContent: DetailContent
    val summary: String?
}
