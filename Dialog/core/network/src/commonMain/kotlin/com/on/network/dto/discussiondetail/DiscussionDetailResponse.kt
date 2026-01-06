package com.on.network.dto.discussiondetail

import com.on.dialog.core.common.extension.toIsoLocalDate
import com.on.dialog.core.common.extension.toIsoLocalDateTime
import com.on.model.discussion.content.Author
import com.on.model.discussion.content.DetailContent
import com.on.model.discussion.content.DiscussionCategory
import com.on.model.discussion.content.DiscussionType
import com.on.model.discussion.content.ProfileImage
import com.on.model.discussion.detail.DiscussionDetail
import com.on.model.discussion.detail.OfflineDiscussionDetail
import com.on.model.discussion.detail.OnlineDiscussionDetail
import com.on.model.discussion.offline.Participant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscussionDetailResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("discussionType")
    val discussionType: String,
    @SerialName("commonDiscussionInfo")
    val commonDiscussionInfoDto: CommonDiscussionInfoDto,
    @SerialName("offlineDiscussionInfo")
    val offlineDiscussionInfoDto: OfflineDiscussionInfoDto?,
    @SerialName("onlineDiscussionInfo")
    val onlineDiscussionInfoDto: OnlineDiscussionInfoDto?,
) {
    fun toDomain(): DiscussionDetail = when (discussionType) {
        "ONLINE" -> {
            OnlineDiscussionDetail(
                detailContent = DetailContent(
                    id = id,
                    discussionType = DiscussionType.of(discussionType),
                    title = commonDiscussionInfoDto.title,
                    author = commonDiscussionInfoDto.authorDto.toDomain(),
                    category = DiscussionCategory.of(commonDiscussionInfoDto.category),
                    content = commonDiscussionInfoDto.content,
                    createdAt = commonDiscussionInfoDto.createdAt.toIsoLocalDateTime(),
                    likeCount = commonDiscussionInfoDto.likeCount,
                    modifiedAt = commonDiscussionInfoDto.modifiedAt.toIsoLocalDateTime(),
                ),
                summary = commonDiscussionInfoDto.summary,
                endDate = onlineDiscussionInfoDto!!.endDate.toIsoLocalDate(),
            )
        }

        "OFFLINE" -> {
            OfflineDiscussionDetail(
                detailContent = DetailContent(
                    id = id,
                    discussionType = DiscussionType.of(discussionType),
                    title = commonDiscussionInfoDto.title,
                    author = commonDiscussionInfoDto.authorDto.toDomain(),
                    category = DiscussionCategory.of(commonDiscussionInfoDto.category),
                    content = commonDiscussionInfoDto.content,
                    createdAt = commonDiscussionInfoDto.createdAt.toIsoLocalDateTime(),
                    likeCount = commonDiscussionInfoDto.likeCount,
                    modifiedAt = commonDiscussionInfoDto.modifiedAt.toIsoLocalDateTime(),
                ),
                summary = commonDiscussionInfoDto.summary,
                startAt = offlineDiscussionInfoDto!!.startAt.toIsoLocalDateTime(),
                endAt = offlineDiscussionInfoDto.endAt.toIsoLocalDateTime(),
                currentParticipantCount = offlineDiscussionInfoDto.participantCount,
                maxParticipantCount = offlineDiscussionInfoDto.maxParticipantCount,
                place = offlineDiscussionInfoDto.place,
                participants = offlineDiscussionInfoDto.participantsDto.map { it.toDomain() },
            )
        }

        else -> {
            throw IllegalArgumentException("없는 타입입니다.")
        }
    }

    @Serializable
    data class CommonDiscussionInfoDto(
        @SerialName("author")
        val authorDto: AuthorDto,
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
        val summary: String?,
        @SerialName("title")
        val title: String,
    ) {
        @Serializable
        data class AuthorDto(
            @SerialName("id")
            val id: Long,
            @SerialName("name")
            val name: String,
            @SerialName("profileImage")
            val profileImageDto: ProfileImageDto?,
        ) {
            fun toDomain(): Author =
                Author(
                    id = id,
                    nickname = name,
                    profileImage = profileImageDto?.toDomain(),
                )

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
    }

    @Serializable
    data class OfflineDiscussionInfoDto(
        @SerialName("endAt")
        val endAt: String,
        @SerialName("maxParticipantCount")
        val maxParticipantCount: Int,
        @SerialName("participantCount")
        val participantCount: Int,
        @SerialName("participants")
        val participantsDto: List<ParticipantDto>,
        @SerialName("place")
        val place: String,
        @SerialName("startAt")
        val startAt: String,
    ) {
        @Serializable
        data class ParticipantDto(
            @SerialName("id")
            val id: Long,
            @SerialName("name")
            val name: String,
        ) {
            fun toDomain(): Participant =
                Participant(
                    id = id,
                    name = name,
                )
        }
    }

    @Serializable
    data class OnlineDiscussionInfoDto(
        @SerialName("endDate")
        val endDate: String,
    )
}
