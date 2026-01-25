package com.on.dialog.ui.mapper

import com.on.model.common.Track
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.track_android
import dialog.core.ui.generated.resources.track_backend
import dialog.core.ui.generated.resources.track_frontend
import org.jetbrains.compose.resources.StringResource

fun Track.toStringResource(): StringResource {
    return when (this) {
        Track.ANDROID -> Res.string.track_android
        Track.BACKEND -> Res.string.track_backend
        Track.FRONTEND -> Res.string.track_frontend
    }
}