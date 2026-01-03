package com.on.dialog.data.dto.response.discussiondetail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnlineDiscussionInfo(
    @SerialName("endDate")
    val endDate: String,
)
