package com.on.dialog.feature.signup.impl.mapper

import com.on.dialog.model.common.Track
import dialog.feature.signup.impl.generated.resources.Res
import dialog.feature.signup.impl.generated.resources.track_android
import dialog.feature.signup.impl.generated.resources.track_backend
import dialog.feature.signup.impl.generated.resources.track_common
import dialog.feature.signup.impl.generated.resources.track_frontend
import org.jetbrains.compose.resources.StringResource

internal fun Track.toFullNameRes(): StringResource = when (this) {
    Track.ANDROID -> Res.string.track_android
    Track.BACKEND -> Res.string.track_backend
    Track.FRONTEND -> Res.string.track_frontend
    Track.COMMON -> Res.string.track_common
}
