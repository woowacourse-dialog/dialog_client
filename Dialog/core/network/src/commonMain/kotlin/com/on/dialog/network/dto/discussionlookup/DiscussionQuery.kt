package com.on.dialog.network.dto.discussionlookup

import com.on.dialog.model.discussion.content.DiscussionStatus
import com.on.dialog.model.discussion.criteria.DiscussionCriteria

data class DiscussionQuery(
    val discussionCriteria: DiscussionCriteria,
) {
    fun toQueryMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()

        discussionCriteria.tracks
            .takeIf { it.isNotEmpty() }
            ?.let { tracks ->
                map["categories"] = tracks.joinToString(",") { it.name.lowercase() }
            }

        discussionCriteria.statuses
            .takeIf { it.isNotEmpty() }
            ?.let { statuses ->
                map["statuses"] = statuses.joinToString(",") { it.toCamelCase() }
            }

        discussionCriteria.discussionTypes
            .takeIf { it.isNotEmpty() }
            ?.let { types ->
                map["discussionTypes"] = types.joinToString(",") { it.name.lowercase() }
            }

        return map
    }

    private fun DiscussionStatus.toCamelCase(): String =
        name.lowercase()
            .split("_")
            .mapIndexed { index, word ->
                if (index == 0) word
                else word.replaceFirstChar { it.uppercase() }
            }
            .joinToString("")

    companion object {
        fun DiscussionCriteria.toQuery(): DiscussionQuery =
            DiscussionQuery(discussionCriteria = this)
    }
}
