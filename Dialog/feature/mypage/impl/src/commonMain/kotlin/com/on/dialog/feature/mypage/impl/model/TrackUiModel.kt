package com.on.dialog.feature.mypage.impl.model

import androidx.compose.runtime.Immutable
import com.on.dialog.feature.mypage.impl.mapper.toFullNameRes
import com.on.dialog.feature.mypage.impl.mapper.toInitial
import com.on.dialog.model.common.Track
import org.jetbrains.compose.resources.StringResource

@Immutable
data class TrackUiModel(
    val initial: String,
    val fullNameRes: StringResource,
) {
    companion object {
        fun Track.toUiModel() = TrackUiModel(
            initial = this.toInitial(),
            fullNameRes = this.toFullNameRes(),
        )
    }
}
