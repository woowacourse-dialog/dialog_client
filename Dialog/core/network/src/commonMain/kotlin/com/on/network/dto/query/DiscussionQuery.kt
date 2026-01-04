package com.on.network.dto.query

import com.on.model.DiscussionCategory
import com.on.model.DiscussionStatus
import com.on.model.DiscussionType

data class DiscussionQuery(
    val categories: List<DiscussionCategory>?,
    val statuses: List<DiscussionStatus>?,
    val discussionTypes: List<DiscussionType>?,
    val cursor: String,
    val size: Int,
) {
    fun toQueryMap(): Map<String, List<String>> {
        val map: MutableMap<String, List<String>> = mutableMapOf<String, List<String>>()

        categories?.let { map["categories"] = it.map { category -> category.name } }
        statuses?.let { map["statuses"] = it.map { status -> status.name } }
        discussionTypes?.let { map["discussionTypes"] = it.map { type -> type.name } }
        map["cursor"] = listOf(cursor)
        map["size"] = listOf(size.toString())

        return map
    }
}
