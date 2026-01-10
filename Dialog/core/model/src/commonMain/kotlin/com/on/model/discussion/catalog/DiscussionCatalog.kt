package com.on.model.discussion.catalog

import com.on.model.discussion.content.CatalogContent
import com.on.model.discussion.content.DiscussionStatus
import kotlinx.datetime.LocalDateTime

sealed interface DiscussionCatalog {
    val catalogContent: CatalogContent

    fun status(now: LocalDateTime): DiscussionStatus
}
