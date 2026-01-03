package com.on.dialog.domain

data class OnlineDiscussion(
    override val content: Content,
    override val summary: String?,
    override val endDate: String,
) : Discussion
