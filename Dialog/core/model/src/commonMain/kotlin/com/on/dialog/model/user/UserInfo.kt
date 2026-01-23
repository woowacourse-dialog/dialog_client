package com.on.dialog.model.user

import com.on.dialog.model.common.Track

data class UserInfo(
    val id: Long,
    val nickname: String,
    val githubId: String,
    val track: com.on.dialog.model.common.Track,
    val isNotificationEnabled: Boolean,
)
