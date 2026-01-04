package com.on.model.discussion.content

enum class DiscussionType {
    UNDEFINED,
    ONLINE,
    OFFLINE,
    ;

    fun of(discussionType: String): DiscussionType = when (discussionType) {
        "ONLINE" -> ONLINE
        "OFFLINE" -> OFFLINE
        else -> UNDEFINED
    }
}