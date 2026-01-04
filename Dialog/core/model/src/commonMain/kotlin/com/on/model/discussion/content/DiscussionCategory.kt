package com.on.model.discussion.content

enum class DiscussionCategory {
    UNDEFINED,
    ANDROID,
    BACKEND,
    FRONTEND,
    ;

    fun of(category: String): DiscussionCategory = when (category) {
        "ANDROID" -> ANDROID
        "BACKEND" -> BACKEND
        "FRONTEND" -> FRONTEND
        else -> UNDEFINED
    }
}