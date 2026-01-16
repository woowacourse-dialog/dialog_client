package com.on.model.common

enum class Track(
    val initial: String,
) {
    ANDROID("AN"),
    BACKEND("BE"),
    FRONTEND("FE"),
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
