package com.on.dialog.model.discussion.catalog

import com.on.dialog.model.discussion.content.CatalogContent
import com.on.dialog.model.discussion.content.DiscussionStatus
import kotlinx.datetime.LocalDateTime

sealed interface DiscussionCatalog {
    val catalogContent: CatalogContent

    fun status(now: LocalDateTime): DiscussionStatus
}
