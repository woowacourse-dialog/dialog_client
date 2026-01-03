package com.on.network.dto.response.discussionlookup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnlineDiscussionInfo(
    @SerialName("endDate")
    val endDate: String,
)
