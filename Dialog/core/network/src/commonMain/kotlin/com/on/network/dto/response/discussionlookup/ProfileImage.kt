package com.on.network.dto.response.discussionlookup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileImage(
    @SerialName("basicImageUri")
    val basicImageUri: String?,
    @SerialName("customImageUri")
    val customImageUri: String?,
)
