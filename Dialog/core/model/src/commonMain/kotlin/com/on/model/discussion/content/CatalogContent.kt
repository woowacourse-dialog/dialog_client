package com.on.model.discussion.content

import kotlinx.datetime.LocalDateTime

data class CatalogContent(
    val id: Long,
    val discussionType: String,
    val title: String,
    val author: String,
    val category: DiscussionCategory,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val commentCount: Int,
    val profileImage: ProfileImage?,
) {
    val status: DiscussionStatus get() =
        when(createdAt > modifiedAt) {
            true -> DiscussionStatus.DISCUSSIONCOMPLETE
            false -> DiscussionStatus.INDISCUSSION
        }
}
