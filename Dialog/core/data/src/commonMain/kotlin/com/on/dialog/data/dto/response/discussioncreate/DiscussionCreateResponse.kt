package com.on.dialog.data.dto.response.discussioncreate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionCreateResponse(
    @SerialName("discussionId")
    val discussionId: Int,
)
