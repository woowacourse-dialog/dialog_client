package com.on.dialog.network.dto.scrap

import com.on.dialog.core.common.extension.toIsoLocalDate
import com.on.dialog.core.common.extension.toIsoLocalDateTime
import com.on.dialog.model.common.ProfileImage
import com.on.dialog.model.common.Track
import com.on.dialog.model.discussion.content.CatalogContent
import com.on.dialog.model.discussion.cursorpage.ScrapCatalogCursorPage
import com.on.dialog.model.discussion.datetimeperiod.DateTimePeriod
import com.on.dialog.model.discussion.datetimeperiod.EndDate
import com.on.dialog.model.discussion.participant.ParticipantCapacity
import com.on.dialog.model.discussion.scrap.OfflineScrapCatalog
import com.on.dialog.model.discussion.scrap.OnlineScrapCatalog
import com.on.dialog.model.discussion.scrap.ScrapCatalog
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScrapCursorPageResponse(
    @SerialName("content")
    val contentDto: List<ContentDto>,
    @SerialName("nextCursorId")
    val nextCursorId: Long?,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("size")
    val size: Int,
) {
    fun toDomain(): ScrapCatalogCursorPage =
        ScrapCatalogCursorPage(
            scrapCatalog = contentDto.map { contentDto: ContentDto ->
                when (contentDto) {
                    is ContentDto.OnlineContentDto -> contentDto.toDomain()
                    is ContentDto.OfflineContentDto -> contentDto.toDomain()
                }
            },
            hasNext = hasNext,
            nextCursorId = nextCursorId,
            size = size,
        )

    @Serializable
    sealed interface ContentDto {
        @Serializable
        @SerialName("ONLINE")
        data class OnlineContentDto(
            @SerialName("id")
            val id: Long,
            @SerialName("discussionType")
            val discussionType: String,
            @SerialName("commonDiscussionInfo")
            val commonDiscussionInfoDto: CommonDiscussionInfoDto,
            @SerialName("onlineDiscussionInfo")
            val onlineDiscussionInfoDto: OnlineDiscussionInfoDto,
        ) : ContentDto {
            fun toDomain(): ScrapCatalog =
                OnlineScrapCatalog(
                    catalogContent = CatalogContent(
                        id = id,
                        title = commonDiscussionInfoDto.title,
                        author = commonDiscussionInfoDto.author,
                        category = Track.valueOf(commonDiscussionInfoDto.category),
                        createdAt = commonDiscussionInfoDto.createdAt.toIsoLocalDateTime(),
                        modifiedAt = commonDiscussionInfoDto.modifiedAt.toIsoLocalDateTime(),
                        commentCount = commonDiscussionInfoDto.commentCount,
                        profileImage = commonDiscussionInfoDto.profileImageDto.toDomain(),
                    ),
                    endDate = EndDate(onlineDiscussionInfoDto.endDate.toIsoLocalDate()),
                )
        }

        @Serializable
        @SerialName("OFFLINE")
        data class OfflineContentDto(
            @SerialName("id")
            val id: Long,
            @SerialName("discussionType")
            val discussionType: String,
            @SerialName("commonDiscussionInfo")
            val commonDiscussionInfoDto: CommonDiscussionInfoDto,
            @SerialName("offlineDiscussionInfo")
            val offlineDiscussionInfoDto: OfflineDiscussionInfoDto,
        ) : ContentDto {
            fun toDomain(): ScrapCatalog =
                OfflineScrapCatalog(
                    catalogContent = CatalogContent(
                        id = id,
                        title = commonDiscussionInfoDto.title,
                        author = commonDiscussionInfoDto.author,
                        category = Track.valueOf(commonDiscussionInfoDto.category),
                        createdAt = commonDiscussionInfoDto.createdAt.toIsoLocalDateTime(),
                        modifiedAt = commonDiscussionInfoDto.modifiedAt.toIsoLocalDateTime(),
                        commentCount = commonDiscussionInfoDto.commentCount,
                        profileImage = commonDiscussionInfoDto.profileImageDto.toDomain(),
                    ),
                    dateTimePeriod = DateTimePeriod(
                        startAt = offlineDiscussionInfoDto.startAt.toIsoLocalDateTime(),
                        endAt = offlineDiscussionInfoDto.endAt.toIsoLocalDateTime(),
                    ),
                    participantCapacity = ParticipantCapacity(
                        current = offlineDiscussionInfoDto.participantCount,
                        max = offlineDiscussionInfoDto.maxParticipantCount,
                    ),
                    place = offlineDiscussionInfoDto.place,
                )
        }

        @Serializable
        data class CommonDiscussionInfoDto(
            @SerialName("title")
            val title: String,
            @SerialName("author")
            val author: String,
            @SerialName("profileImage")
            val profileImageDto: ProfileImageDto,
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

        @Serializable
        data class OfflineDiscussionInfoDto(
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
        data class OnlineDiscussionInfoDto(
            @SerialName("endDate")
            val endDate: String,
        )
    }
}
