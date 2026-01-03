package com.on.network.dto.response.discussionlookup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommonDiscussionInfo(
    @SerialName("title")
    val title: String,
    @SerialName("author")
    val author: String,
    @SerialName("profileImage")
    val profileImage: ProfileImage?,
    @SerialName("category")
    val category: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("modifiedAt")
    val modifiedAt: String,
    @SerialName("commentCount")
    val commentCount: Int,
)
