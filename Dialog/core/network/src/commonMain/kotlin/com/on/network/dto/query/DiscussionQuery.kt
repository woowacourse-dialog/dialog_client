package com.on.network.dto.query

import com.on.model.discussion.criteria.DiscussionCriteria

data class DiscussionQuery(
    val discussionCriteria: DiscussionCriteria,
    val cursor: String,
    val size: Int,
) {
    fun toQueryMap(): Map<String, List<String>> {
        val map: MutableMap<String, List<String>> = mutableMapOf<String, List<String>>()

        discussionCriteria.categories?.let { map["categories"] = it.map { category -> category.name } }
        discussionCriteria.statuses?.let { map["statuses"] = it.map { status -> status.name } }
        discussionCriteria.discussionTypes?.let { map["discussionTypes"] = it.map { type -> type.name } }
        map["cursor"] = listOf(cursor)
        map["size"] = listOf(size.toString())

        return map
    }
}
