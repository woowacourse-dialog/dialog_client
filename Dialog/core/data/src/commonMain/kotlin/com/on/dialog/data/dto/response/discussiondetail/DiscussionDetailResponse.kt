package com.on.dialog.data.dto.response.discussiondetail

import com.on.dialog.data.dto.response.discussiondetail.CommonDiscussionInfo
import com.on.dialog.data.dto.response.discussiondetail.OfflineDiscussionInfo
import com.on.dialog.data.dto.response.discussiondetail.OnlineDiscussionInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionDetailResponse(
    @SerialName("commonDiscussionInfo")
    val commonDiscussionInfo: CommonDiscussionInfo?,
    @SerialName("discussionType")
    val discussionType: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("offlineDiscussionInfo")
    val offlineDiscussionInfo: OfflineDiscussionInfo?,
    @SerialName("onlineDiscussionInfo")
    val onlineDiscussionInfo: OnlineDiscussionInfo?,
)
