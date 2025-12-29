package com.on.dialog.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionDetailResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("author")
    val authorResponse: AuthorResponse,
    @SerialName("category")
    val category: String,
    @SerialName("commentCount")
    val commentCount: Int,
    @SerialName("content")
    val content: String,
    @SerialName("currentParticipantCount")
    val currentParticipantCount: Int?,
    @SerialName("discussionType")
    val discussionType: String,
    @SerialName("likeCount")
    val likeCount: Int,
    @SerialName("maxParticipantCount")
    val maxParticipantCount: Int?,
    @SerialName("place")
    val place: String?,
    @SerialName("profileImage")
    val profileImageResponse: ProfileImageResponse,
    @SerialName("scrapCount")
    val scrapCount: Int,
    @SerialName("startAt")
    val startAt: String?,
    @SerialName("status")
    val status: String,
    @SerialName("summary")
    val summary: String?,
    @SerialName("viewCount")
    val viewCount: Int,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("endDate")
    val endDate: String?,
    @SerialName("endAt")
    val endAt: String?,
)
