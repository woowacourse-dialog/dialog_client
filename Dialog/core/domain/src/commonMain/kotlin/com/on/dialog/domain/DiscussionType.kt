package com.on.dialog.domain

enum class DiscussionType {
    UNDEFINED,
    ONLINE,
    OFFLINE,
    ;

    fun set(discussionType: String): DiscussionType = when (discussionType) {
        "ONLINE" -> ONLINE
        "OFFLINE" -> OFFLINE
        else -> UNDEFINED
    }
}
