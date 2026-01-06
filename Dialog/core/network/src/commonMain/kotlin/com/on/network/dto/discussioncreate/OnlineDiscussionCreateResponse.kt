package com.on.network.dto.discussioncreate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnlineDiscussionCreateResponse(
    @SerialName("discussionId")
    val discussionId: Long,
)
