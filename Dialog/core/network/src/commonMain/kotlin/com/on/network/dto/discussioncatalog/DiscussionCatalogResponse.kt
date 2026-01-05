package com.on.network.dto.discussioncatalog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionCatalogResponse(
    @SerialName("content")
    val content: List<Content>,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("nextCursor")
    val nextCursor: String,
) {
    @Serializable
    data class Content(
        @SerialName("id")
        val id: Long,
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
        ) {
            @Serializable
            data class ProfileImage(
                @SerialName("basicImageUri")
                val basicImageUri: String?,
                @SerialName("customImageUri")
                val customImageUri: String?,
            )
        }

        @Serializable
        data class OfflineDiscussionInfo(
            @SerialName("endAt")
            val endAt: String,
            @SerialName("maxParticipantCount")
            val maxParticipantCount: Int,
            @SerialName("participantCount")
            val participantCount: Int,
            @SerialName("place")
            val place: String,
            @SerialName("startAt")
            val startAt: String,
        )
        @Serializable
        data class OnlineDiscussionInfo(
            @SerialName("endDate")
            val endDate: String,
        )
    }
}
