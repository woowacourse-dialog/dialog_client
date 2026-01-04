package com.on.model.discussion

import com.on.model.discussion.Content

sealed interface Discussion {
    val content: Content
    val summary: String?
}
