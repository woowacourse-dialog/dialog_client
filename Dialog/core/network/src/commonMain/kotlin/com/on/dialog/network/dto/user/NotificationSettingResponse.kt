package com.on.dialog.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NotificationSettingResponse(
    @SerialName("isNotificationEnable")
    val isNotificationEnable: Boolean,
)
