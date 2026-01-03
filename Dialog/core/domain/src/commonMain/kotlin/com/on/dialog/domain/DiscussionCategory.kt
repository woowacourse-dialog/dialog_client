package com.on.dialog.domain

enum class DiscussionCategory {
    UNDEFINED,
    ANDROID,
    BACKEND,
    FRONTEND,
    ;

    fun set(category: String): DiscussionCategory = when (category) {
        "ANDROID" -> ANDROID
        "BACKEND" -> BACKEND
        "FRONTEND" -> FRONTEND
        else -> UNDEFINED
    }
}
