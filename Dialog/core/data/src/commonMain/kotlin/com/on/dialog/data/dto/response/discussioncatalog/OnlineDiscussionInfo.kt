package com.on.dialog.data.dto.response.discussioncatalog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnlineDiscussionInfo(
    @SerialName("endDate")
    val endDate: String,
)
