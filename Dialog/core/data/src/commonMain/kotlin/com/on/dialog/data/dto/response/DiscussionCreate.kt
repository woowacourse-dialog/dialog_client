package com.on.dialog.data.dto.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionCreate(
    @SerialName("discussionId")
    val discussionId: Int
)