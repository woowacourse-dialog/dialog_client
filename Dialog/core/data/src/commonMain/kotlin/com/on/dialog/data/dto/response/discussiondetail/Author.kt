package com.on.dialog.data.dto.response.discussiondetail

import com.on.dialog.data.dto.response.discussiondetail.ProfileImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Author(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profileImage")
    val profileImage: ProfileImage?,
)
