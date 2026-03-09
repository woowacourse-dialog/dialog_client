package com.on.dialog.feature.creatediscussion.impl.mapper

import com.on.dialog.model.common.Track
import dialog.feature.creatediscussion.impl.generated.resources.Res
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_track_android
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_track_backend
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_track_common
import dialog.feature.creatediscussion.impl.generated.resources.create_discussion_track_frontend
import org.jetbrains.compose.resources.StringResource

internal fun Track.toFullNameRes(): StringResource = when (this) {
    Track.ANDROID -> Res.string.create_discussion_track_android
    Track.BACKEND -> Res.string.create_discussion_track_backend
    Track.FRONTEND -> Res.string.create_discussion_track_frontend
    Track.COMMON -> Res.string.create_discussion_track_common
}

internal fun Int.toTrackCategory(): String = when (this) {
    0 -> Track.ANDROID.name
    1 -> Track.BACKEND.name
    2 -> Track.FRONTEND.name
    else -> Track.COMMON.name
}

internal fun Int.toEndDateOffsetDays(): Int = when (this) {
    0 -> 1
    1 -> 2
    else -> 3
}
