package com.on.network.dto.discussioncreate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfflineDiscussionCreateResponse(
    @SerialName("discussionId")
    val discussionId: Long,
)