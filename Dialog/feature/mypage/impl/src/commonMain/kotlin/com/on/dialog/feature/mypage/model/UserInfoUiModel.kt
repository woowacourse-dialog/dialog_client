package com.on.dialog.feature.mypage.model

import com.on.dialog.model.common.Track
import com.on.dialog.model.user.UserInfo

data class UserInfoUiModel(
    val id: Long,
    val nickname: String,
    val githubId: String,
    val track: String,
    val isNotificationEnabled: Boolean,
) {
    companion object {
        fun UserInfo.toDomain() = UserInfoUiModel(
            id = id,
            nickname = nickname,
            githubId = githubId,
            track = when (track) {
                Track.ANDROID -> "AN"
                Track.BACKEND -> "BE"
                Track.FRONTEND -> "FE"
                else -> "알 수 없음"
            },
            isNotificationEnabled = isNotificationEnabled,
        )
    }
}
