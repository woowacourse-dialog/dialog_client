package com.on.model.user

import com.on.model.common.Track

data class UserInfo(
    val id: Long,
    val nickname: String,
    val githubId: String,
    val track: Track,
    val isNotificationEnabled: Boolean,
)
