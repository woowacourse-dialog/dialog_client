package com.on.dialog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileImageResponse(
    @SerialName("basicImageUri")
    val basicImageUri: String,
    @SerialName("customImageUri")
    val customImageUri: String,
)
