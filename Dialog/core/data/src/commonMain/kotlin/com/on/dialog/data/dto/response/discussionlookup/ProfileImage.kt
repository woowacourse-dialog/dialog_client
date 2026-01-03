package com.on.dialog.data.dto.response.discussionlookup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileImage(
    @SerialName("basicImageUri")
    val basicImageUri: String?,
    @SerialName("customImageUri")
    val customImageUri: String?,
)
