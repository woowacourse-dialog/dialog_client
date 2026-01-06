package com.on.network.dto.discussionlookup

import com.on.model.discussion.criteria.DiscussionCriteria

data class DiscussionQuery(
    val discussionCriteria: DiscussionCriteria,
) {
    fun toQueryMap(): Map<String, List<String>> {
        val map: MutableMap<String, List<String>> = mutableMapOf()

        discussionCriteria.categories?.let { map["categories"] = it.map { category -> category.name } }
        discussionCriteria.statuses?.let { map["statuses"] = it.map { status -> status.name } }
        discussionCriteria.discussionTypes?.let { map["discussionTypes"] = it.map { type -> type.name } }

        return map
    }

    companion object {
        fun DiscussionCriteria.toQuery(): DiscussionQuery =
            DiscussionQuery(discussionCriteria = this)
    }
}
