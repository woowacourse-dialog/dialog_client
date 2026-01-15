package com.on.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NotificationSettingRequest(
    @SerialName("isNotificationEnable")
    val isNotificationEnable: Boolean,
)
