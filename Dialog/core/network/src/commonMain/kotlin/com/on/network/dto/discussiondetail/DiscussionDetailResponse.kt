package com.on.network.dto.discussiondetail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionDetailResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("discussionType")
    val discussionType: String,
    @SerialName("commonDiscussionInfo")
    val commonDiscussionInfo: CommonDiscussionInfo,
    @SerialName("offlineDiscussionInfo")
    val offlineDiscussionInfo: OfflineDiscussionInfo?,
    @SerialName("onlineDiscussionInfo")
    val onlineDiscussionInfo: OnlineDiscussionInfo?,
) {
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
    ) {
        @Serializable
        data class Author(
            @SerialName("id")
            val id: Long,
            @SerialName("name")
            val name: String,
            @SerialName("profileImage")
            val profileImage: ProfileImage?,
        ){
            @Serializable
            data class ProfileImage(
                @SerialName("basicImageUri")
                val basicImageUri: String?,
                @SerialName("customImageUri")
                val customImageUri: String?,
            )

        }
    }

    @Serializable
    data class OfflineDiscussionInfo(
        @SerialName("endAt")
        val endAt: String,
        @SerialName("maxParticipantCount")
        val maxParticipantCount: Int,
        @SerialName("participantCount")
        val participantCount: Int,
        @SerialName("participants")
        val participants: List<Participant?>,
        @SerialName("place")
        val place: String,
        @SerialName("startAt")
        val startAt: String,
    ) {
        @Serializable
        data class Participant(
            @SerialName("id")
            val id: Int,
            @SerialName("name")
            val name: String,
        )
    }

    @Serializable
    data class OnlineDiscussionInfo(
        @SerialName("endDate")
        val endDate: String,
    )
}
