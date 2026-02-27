package com.on.dialog.model.discussion.scrap

import com.on.dialog.model.discussion.content.CatalogContent
import com.on.dialog.model.discussion.content.DiscussionStatus
import kotlinx.datetime.LocalDateTime

sealed interface ScrapCatalog {
    val catalogContent: CatalogContent

    fun status(now: LocalDateTime): DiscussionStatus
}
