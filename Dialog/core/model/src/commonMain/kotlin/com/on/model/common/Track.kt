package com.on.model.common

enum class Track {
    ANDROID,
    BACKEND,
    FRONTEND,
    ;

    companion object {
        fun of(name: String): Track = when (name) {
            "ANDROID" -> ANDROID
            "BACKEND" -> BACKEND
            "FRONTEND" -> FRONTEND
            else -> ANDROID
        }
    }
}
