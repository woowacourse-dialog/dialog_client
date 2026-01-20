package com.on.network.dto.user

import com.on.model.common.Track
import com.on.model.user.UserInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserInfoResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("githubId")
    val githubId: String,
    @SerialName("track")
    val track: String,
    @SerialName("isNotificationEnabled")
    val isNotificationEnabled: Boolean,
) {
    fun toDomain(): UserInfo =
        UserInfo(
            id = id,
            nickname = nickname,
            githubId = githubId,
            track = Track.of(track),
            isNotificationEnabled = isNotificationEnabled,
        )
}
