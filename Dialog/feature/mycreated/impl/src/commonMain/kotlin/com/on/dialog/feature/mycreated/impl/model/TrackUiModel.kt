package com.on.dialog.feature.mycreated.impl.model

import com.on.dialog.model.common.Track

internal enum class TrackUiModel(
    val title: String,
) {
    COMMON(title = "공통"),
    ANDROID(title = "안드로이드"),
    BACKEND(title = "백엔드"),
    FRONTEND(title = "프론트엔드"),
    ;

    fun toDomain(): Track = when (this) {
        COMMON -> Track.COMMON
        ANDROID -> Track.ANDROID
        BACKEND -> Track.BACKEND
        FRONTEND -> Track.FRONTEND
    }

    companion object {
        fun Track.toUiModel(): TrackUiModel = when (this) {
            Track.COMMON -> COMMON
            Track.ANDROID -> ANDROID
            Track.BACKEND -> BACKEND
            Track.FRONTEND -> FRONTEND
        }
    }
}
