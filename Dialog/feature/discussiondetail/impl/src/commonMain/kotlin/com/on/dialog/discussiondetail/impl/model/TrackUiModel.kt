package com.on.dialog.discussiondetail.impl.model

import androidx.compose.runtime.Composable
import com.on.dialog.model.common.Track
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.track_android
import dialog.feature.discussiondetail.impl.generated.resources.track_backend
import dialog.feature.discussiondetail.impl.generated.resources.track_common
import dialog.feature.discussiondetail.impl.generated.resources.track_frontend
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

enum class TrackUiModel(
    private val titleResId: StringResource,
) {
    COMMON(titleResId = Res.string.track_common),
    ANDROID(titleResId = Res.string.track_android),
    BACKEND(titleResId = Res.string.track_backend),
    FRONTEND(titleResId = Res.string.track_frontend),
    ;

    val title: String
        @Composable get() = stringResource(titleResId)

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
