package com.on.dialog.network.dto.user

import com.on.dialog.model.common.ProfileImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProfileImageGetResponse(
    @SerialName("customImageUri")
    val customImageUri: String?,
    @SerialName("basicImageUri")
    val basicImageUri: String,
) {
    fun toDomain(): ProfileImage =
        ProfileImage(
            customImageUri = customImageUri,
            basicImageUri = basicImageUri,
        )
}
