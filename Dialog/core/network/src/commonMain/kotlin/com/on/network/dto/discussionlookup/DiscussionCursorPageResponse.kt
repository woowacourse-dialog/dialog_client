package com.on.network.dto.discussionlookup

import com.on.dialog.core.common.extension.toIsoLocalDateTime
import com.on.model.discussion.catalog.DiscussionCatalog
import com.on.model.discussion.catalog.OfflineDiscussionCatalog
import com.on.model.discussion.catalog.OnlineDiscussionCatalog
import com.on.model.discussion.content.CatalogContent
import com.on.model.discussion.content.DiscussionCategory
import com.on.model.discussion.content.ProfileImage
import com.on.model.discussion.cursorpage.DiscussionCatalogCursorPage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionCursorPageResponse(
    @SerialName("content")
    val contentDto: List<ContentDto>,
    @SerialName("hasNext")
    val hasNext: Boolean,
    @SerialName("nextCursor")
    val nextCursor: String,
) {
    fun toDomain(): DiscussionCatalogCursorPage =
        DiscussionCatalogCursorPage(
            discussionCatalog = contentDto.map { it.toDomain() },
            hasNext = hasNext,
            nextCursor = nextCursor,
        )

    @Serializable
    data class ContentDto(
        @SerialName("id")
        val id: Long,
        @SerialName("discussionType")
        val discussionType: String,
        @SerialName("commonDiscussionInfo")
        val commonDiscussionInfoDto: CommonDiscussionInfoDto,
        @SerialName("offlineDiscussionInfo")
        val offlineDiscussionInfoDto: OfflineDiscussionInfoDto?,
        @SerialName("onlineDiscussionInfo")
        val onlineDiscussionInfoDto: OnlineDiscussionInfoDto?,
    ) {
        fun toDomain(): DiscussionCatalog =
            when (discussionType) {
                "ONLINE" -> {
                    OnlineDiscussionCatalog(
                        catalogContent = CatalogContent(
                            id = id,
                            title = commonDiscussionInfoDto.title,
                            discussionType = discussionType,
                            author = commonDiscussionInfoDto.author,
                            category = DiscussionCategory.of(commonDiscussionInfoDto.category),
                            createdAt = commonDiscussionInfoDto.createdAt.toIsoLocalDateTime(),
                            modifiedAt = commonDiscussionInfoDto.modifiedAt.toIsoLocalDateTime(),
                            commentCount = commonDiscussionInfoDto.commentCount,
                            profileImage = commonDiscussionInfoDto.profileImageDto?.toDomain(),
                        ),
                        endDate = onlineDiscussionInfoDto!!.endDate,
                    )
                }

                "OFFLINE" -> {
                    OfflineDiscussionCatalog(
                        catalogContent = CatalogContent(
                            id = id,
                            title = commonDiscussionInfoDto.title,
                            discussionType = discussionType,
                            author = commonDiscussionInfoDto.author,
                            category = DiscussionCategory.of(commonDiscussionInfoDto.category),
                            createdAt = commonDiscussionInfoDto.createdAt.toIsoLocalDateTime(),
                            modifiedAt = commonDiscussionInfoDto.modifiedAt.toIsoLocalDateTime(),
                            commentCount = commonDiscussionInfoDto.commentCount,
                            profileImage = commonDiscussionInfoDto.profileImageDto?.toDomain(),
                        ),
                        startAt = offlineDiscussionInfoDto!!.startAt.toIsoLocalDateTime(),
                        endAt = offlineDiscussionInfoDto.endAt.toIsoLocalDateTime(),
                        place = offlineDiscussionInfoDto.place,
                        maxParticipantCount = offlineDiscussionInfoDto.maxParticipantCount,
                        participantCount = offlineDiscussionInfoDto.participantCount,
                    )
                }

                else -> {
                    throw IllegalArgumentException("없는 타입입니다.")
                }
            }

        @Serializable
        data class CommonDiscussionInfoDto(
            @SerialName("title")
            val title: String,
            @SerialName("author")
            val author: String,
            @SerialName("profileImage")
            val profileImageDto: ProfileImageDto?,
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
                val basicImageUri: String?,
                @SerialName("customImageUri")
                val customImageUri: String?,
            ) {
                fun toDomain(): ProfileImage =
                    ProfileImage(
                        basicImageUri = basicImageUri,
                        customImageUri = customImageUri,
                    )
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
