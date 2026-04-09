package com.on.dialog.model.user

import com.on.dialog.model.common.Track

data class UserInfo(
    val id: Long,
    val nickname: String,
    val githubId: String?,
    val track: Track,
    val isNotificationEnabled: Boolean,
)
