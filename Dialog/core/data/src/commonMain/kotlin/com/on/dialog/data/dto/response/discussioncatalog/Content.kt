package com.on.dialog.data.dto.response.discussioncatalog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Content(
    @SerialName("id")
    val id: Int,
    @SerialName("discussionType")
    val discussionType: String,
    @SerialName("commonDiscussionInfo")
    val commonDiscussionInfo: CommonDiscussionInfo,
    @SerialName("offlineDiscussionInfo")
    val offlineDiscussionInfo: OfflineDiscussionInfo?,
    @SerialName("onlineDiscussionInfo")
    val onlineDiscussionInfo: OnlineDiscussionInfo?,
)
