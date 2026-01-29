package com.on.dialog.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NotificationSettingRequest(
    @SerialName("isNotificationEnable")
    val isNotificationEnable: Boolean,
) {
    companion object {
        fun Boolean.toRequest(): NotificationSettingRequest =
            NotificationSettingRequest(isNotificationEnable = this)
    }
}
