package com.on.dialog.feature.mypage.impl.mapper

import com.on.dialog.model.common.Track
import dialog.feature.mypage.impl.generated.resources.Res
import dialog.feature.mypage.impl.generated.resources.track_android
import dialog.feature.mypage.impl.generated.resources.track_backend
import dialog.feature.mypage.impl.generated.resources.track_common
import dialog.feature.mypage.impl.generated.resources.track_frontend
import org.jetbrains.compose.resources.StringResource

internal fun Track.toInitial(): String = when (this) {
    Track.ANDROID -> "AN"
    Track.BACKEND -> "BE"
    Track.FRONTEND -> "FE"
    else -> "UNKNOWN"
}

internal fun Track.toFullNameRes(): StringResource = when (this) {
    Track.ANDROID -> Res.string.track_android
    Track.BACKEND -> Res.string.track_backend
    Track.FRONTEND -> Res.string.track_frontend
    Track.COMMON -> Res.string.track_common
}
