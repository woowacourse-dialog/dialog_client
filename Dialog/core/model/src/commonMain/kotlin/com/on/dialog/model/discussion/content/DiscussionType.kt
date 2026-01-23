package com.on.dialog.model.discussion.content

enum class DiscussionType {
    UNDEFINED,
    ONLINE,
    OFFLINE,
    ;

    companion object {
        fun of(discussionType: String): DiscussionType = when (discussionType) {
            "ONLINE" -> ONLINE
            "OFFLINE" -> OFFLINE
            else -> UNDEFINED
        }
    }
}
