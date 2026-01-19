package com.on.model.common

enum class Track(
    val initial: String,
) {
    ANDROID(initial = "AN"),
    BACKEND(initial = "BE"),
    FRONTEND(initial = "FE"),
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
