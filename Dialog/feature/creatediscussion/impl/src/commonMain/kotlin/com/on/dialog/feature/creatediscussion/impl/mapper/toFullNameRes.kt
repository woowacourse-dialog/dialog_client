package com.on.dialog.feature.creatediscussion.impl.mapper

import com.on.dialog.model.common.Track

internal fun Track.toFullNameRes(): String = when (this) {
    Track.ANDROID -> "안드로이드"
    Track.BACKEND -> "백엔드"
    Track.FRONTEND -> "프론트엔드"
    Track.COMMON -> "공통"
}
