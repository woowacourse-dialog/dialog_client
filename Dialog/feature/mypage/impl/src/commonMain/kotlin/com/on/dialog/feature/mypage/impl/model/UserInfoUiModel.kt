package com.on.dialog.feature.mypage.impl.model

import androidx.compose.runtime.Immutable
import com.on.dialog.feature.mypage.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.model.common.Track
import com.on.dialog.model.user.UserInfo

@Immutable
data class UserInfoUiModel(
    val id: Long = 0L,
    val nickname: String = "",
    val githubId: String = "",
    val track: TrackUiModel = Track.COMMON.toUiModel(),
    val isNotificationEnabled: Boolean = false,
) {
    companion object {
        fun UserInfo.toUiModel() = UserInfoUiModel(
            id = id,
            nickname = nickname,
            githubId = githubId,
            track = track.toUiModel(),
            isNotificationEnabled = isNotificationEnabled,
        )
    }
}
