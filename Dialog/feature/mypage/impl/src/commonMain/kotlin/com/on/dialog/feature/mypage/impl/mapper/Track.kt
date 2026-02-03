package com.on.dialog.feature.mypage.impl.mapper

import androidx.compose.runtime.Composable
import com.on.dialog.model.common.Track
import dialog.feature.mypage.impl.generated.resources.Res
import dialog.feature.mypage.impl.generated.resources.track_android
import dialog.feature.mypage.impl.generated.resources.track_backend
import dialog.feature.mypage.impl.generated.resources.track_common
import dialog.feature.mypage.impl.generated.resources.track_frontend
import org.jetbrains.compose.resources.stringResource

internal fun Track.toInitial(): String {
    return when (this) {
        Track.ANDROID -> "AN"
        Track.BACKEND -> "BE"
        Track.FRONTEND -> "FE"
        else -> "UNKNOWN"
    }
}

@Composable
internal fun Track.toFullName(): String {
    return when (this) {
        Track.ANDROID -> stringResource(resource = Res.string.track_android)
        Track.BACKEND -> stringResource(resource = Res.string.track_backend)
        Track.FRONTEND -> stringResource(resource = Res.string.track_frontend)
        Track.COMMON -> stringResource(resource = Res.string.track_common)
    }
}