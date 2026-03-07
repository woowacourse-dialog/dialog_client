package com.on.dialog.network.dto.scrap

import com.on.dialog.core.common.extension.toIsoLocalDate
import com.on.dialog.core.common.extension.toIsoLocalDateTime
import com.on.dialog.model.common.ProfileImage
import com.on.dialog.model.common.Track
import com.on.dialog.model.discussion.content.CatalogContent
import com.on.dialog.model.discussion.datetimeperiod.DateTimePeriod
import com.on.dialog.model.discussion.datetimeperiod.EndDate
import com.on.dialog.model.discussion.participant.ParticipantCapacity
import com.on.dialog.model.discussion.scrap.OfflineScrapCatalog
import com.on.dialog.model.discussion.scrap.OnlineScrapCatalog
import com.on.dialog.model.discussion.scrap.ScrapCatalog
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScrapSuccessResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("discussionType")
    val discussionType: String,
    @SerialName("commonDiscussionInfo")
    val commonDiscussionInfo: CommonDiscussionInfoDto,
    @SerialName("offlineDiscussionInfo")
    val offlineDiscussionInfo: OfflineDiscussionInfoDto? = null,
    @SerialName("onlineDiscussionInfo")
    val onlineDiscussionInfo: OnlineDiscussionInfoDto? = null,
) {
    fun toDomain(): ScrapCatalog {
        val catalogContent = CatalogContent(
            id = id,
            title = commonDiscussionInfo.title,
            author = commonDiscussionInfo.author.name,
            category = Track.valueOf(commonDiscussionInfo.category.uppercase()),
            createdAt = commonDiscussionInfo.createdAt.toIsoLocalDateTime(),
            modifiedAt = commonDiscussionInfo.modifiedAt.toIsoLocalDateTime(),
            commentCount = 0,
            profileImage = commonDiscussionInfo.author.profileImage.toDomain(),
        )

        return when (discussionType.uppercase()) {
            "ONLINE" -> {
                val onlineInfo = requireNotNull(onlineDiscussionInfo)
                OnlineScrapCatalog(
                    catalogContent = catalogContent,
                    endDate = EndDate(onlineInfo.endDate.toIsoLocalDate()),
                )
            }

            "OFFLINE" -> {
                val offlineInfo = requireNotNull(offlineDiscussionInfo)
                OfflineScrapCatalog(
                    catalogContent = catalogContent,
                    dateTimePeriod = DateTimePeriod(
                        startAt = offlineInfo.startAt.toIsoLocalDateTime(),
                        endAt = offlineInfo.endAt.toIsoLocalDateTime(),
                    ),
                    participantCapacity = ParticipantCapacity(
                        current = offlineInfo.participantCount,
                        max = offlineInfo.maxParticipantCount,
                    ),
                    place = offlineInfo.place,
                )
            }

            else -> error("Unsupported discussionType: $discussionType")
        }
    }

    @Serializable
    data class CommonDiscussionInfoDto(
        @SerialName("title")
        val title: String,
        @SerialName("content")
        val content: String,
        @SerialName("summary")
        val summary: String?,
        @SerialName("category")
        val category: String,
        @SerialName("createdAt")
        val createdAt: String,
        @SerialName("modifiedAt")
        val modifiedAt: String,
        @SerialName("likeCount")
        val likeCount: Int,
        @SerialName("author")
        val author: AuthorDto,
    ) {
        @Serializable
        data class AuthorDto(
            @SerialName("id")
            val id: Long,
            @SerialName("name")
            val name: String,
            @SerialName("profileImage")
            val profileImage: ProfileImageDto,
        ) {
            @Serializable
            data class ProfileImageDto(
                @SerialName("basicImageUri")
                val basicImageUri: String,
                @SerialName("customImageUri")
                val customImageUri: String?,
            ) {
                fun toDomain(): ProfileImage =
                    ProfileImage(basicImageUri = basicImageUri, customImageUri = customImageUri)
            }
        }
    }

    @Serializable
    data class OfflineDiscussionInfoDto(
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
    data class OnlineDiscussionInfoDto(
        @SerialName("endDate")
        val endDate: String,
    )
}
