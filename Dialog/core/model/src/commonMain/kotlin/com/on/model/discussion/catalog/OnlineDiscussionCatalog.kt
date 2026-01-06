package com.on.model.discussion.catalog

import com.on.model.discussion.content.CatalogContent

data class OnlineDiscussionCatalog (
    override val catalogContent: CatalogContent,
    val endDate: String,
) : DiscussionCatalog