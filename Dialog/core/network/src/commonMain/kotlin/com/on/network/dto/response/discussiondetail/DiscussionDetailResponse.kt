package com.on.network.dto.response.discussiondetail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionDetailResponse(
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
