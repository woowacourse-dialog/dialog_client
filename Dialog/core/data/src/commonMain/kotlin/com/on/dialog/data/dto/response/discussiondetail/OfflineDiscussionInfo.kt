package com.on.dialog.data.dto.response.discussiondetail

import com.on.dialog.data.dto.response.discussiondetail.Participant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfflineDiscussionInfo(
    @SerialName("endAt")
    val endAt: String,
    @SerialName("maxParticipantCount")
    val maxParticipantCount: Int,
    @SerialName("participantCount")
    val participantCount: Int,
    @SerialName("participants")
    val participants: List<Participant?>,
    @SerialName("place")
    val place: String,
    @SerialName("startAt")
    val startAt: String,
)
