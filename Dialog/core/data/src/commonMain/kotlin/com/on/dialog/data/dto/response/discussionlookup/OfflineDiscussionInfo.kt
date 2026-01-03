package com.on.dialog.data.dto.response.discussionlookup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfflineDiscussionInfo(
    @SerialName("startAt")
    val startAt: String,
    @SerialName("endAt")
    val endAt: String,
    @SerialName("place")
    val place: String,
    @SerialName("participantCount")
    val participantCount: Int,
    @SerialName("maxParticipantCount")
    val maxParticipantCount: Int,
)
