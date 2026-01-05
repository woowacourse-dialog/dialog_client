package com.on.network.dto.response.discussionlookup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionCursorPageResponse(
    @SerialName("content")
    val content: List<Content?>,
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
            @SerialName("startAt")
            val startAt: String,
            @SerialName("endAt")
            val endAt: String,
            @SerialName("place")
            val place: String,
            @SerialName("participantCount")
            val participantCount: Int,
            @SerialName("maxParticipantCount")
            val maxParticipantCount: Int,
        )

        @Serializable
        data class OnlineDiscussionInfo(
            @SerialName("endDate")
            val endDate: String,
        )

    }

}
