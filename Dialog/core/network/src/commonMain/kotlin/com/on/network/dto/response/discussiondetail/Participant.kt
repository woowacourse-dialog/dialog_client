package com.on.network.dto.response.discussiondetail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Participant(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
)
