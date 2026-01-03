package com.on.network.dto.response.discussiondetail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommonDiscussionInfo(
    @SerialName("author")
    val author: Author,
    @SerialName("category")
    val category: String,
    @SerialName("content")
    val content: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("likeCount")
    val likeCount: Int,
    @SerialName("modifiedAt")
    val modifiedAt: String,
    @SerialName("summary")
    val summary: String,
    @SerialName("title")
    val title: String,
)
