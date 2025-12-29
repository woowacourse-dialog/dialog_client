package com.on.dialog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionPreviewResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("author")
    val authorResponse: AuthorResponse,
    @SerialName("category")
    val category: String,
    @SerialName("commentCount")
    val commentCount: Int,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("discussionType")
    val discussionType: String,
    @SerialName("likeCount")
    val likeCount: Int,
    @SerialName("profileImage")
    val profileImageResponse: ProfileImageResponse,
    @SerialName("scrapCount")
    val scrapCount: Int,
    @SerialName("status")
    val status: String,
    @SerialName("title")
    val title: String,
    @SerialName("viewCount")
    val viewCount: Int,
)
