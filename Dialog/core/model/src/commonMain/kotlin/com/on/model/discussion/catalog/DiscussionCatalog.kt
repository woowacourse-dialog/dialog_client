package com.on.model.discussion.catalog

import com.on.model.discussion.content.CatalogContent

sealed interface DiscussionCatalog {
    val catalogContent: CatalogContent
}