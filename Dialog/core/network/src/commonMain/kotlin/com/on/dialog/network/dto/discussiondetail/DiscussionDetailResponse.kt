package com.on.dialog.network.dto.discussiondetail

import com.on.dialog.core.common.extension.toIsoLocalDate
import com.on.dialog.core.common.extension.toIsoLocalDateTime
import com.on.dialog.model.common.ProfileImage
import com.on.dialog.model.common.Track
import com.on.dialog.model.discussion.content.Author
import com.on.dialog.model.discussion.content.DetailContent
import com.on.dialog.model.discussion.datetimeperiod.DateTimePeriod
import com.on.dialog.model.discussion.datetimeperiod.EndDate
import com.on.dialog.model.discussion.detail.DiscussionDetail
import com.on.dialog.model.discussion.detail.OfflineDiscussionDetail
import com.on.dialog.model.discussion.detail.OnlineDiscussionDetail
import com.on.dialog.model.discussion.offline.Participant
import com.on.dialog.model.discussion.participant.ParticipantCapacity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface DiscussionDetailResponse {
    @Serializable
    @SerialName("ONLINE")
    data class DiscussionDetailOnlineResponse(
        @SerialName("id")
        val id: Long,
        @SerialName("commonDiscussionInfo")
        val commonDiscussionInfoDto: CommonDiscussionInfoDto,
        @SerialName("onlineDiscussionInfo")
        val onlineDiscussionInfoDto: OnlineDiscussionInfoDto,
    ) : DiscussionDetailResponse {
        fun toDomain(): DiscussionDetail =
            OnlineDiscussionDetail(
                detailContent = DetailContent(
                    id = id,
                    title = commonDiscussionInfoDto.title,
                    author = commonDiscussionInfoDto.authorDto.toDomain(),
                    category = Track.valueOf(commonDiscussionInfoDto.category),
                    content = commonDiscussionInfoDto.content,
                    createdAt = commonDiscussionInfoDto.createdAt.toIsoLocalDateTime(),
                    likeCount = commonDiscussionInfoDto.likeCount,
                    modifiedAt = commonDiscussionInfoDto.modifiedAt.toIsoLocalDateTime(),
                ),
                summary = commonDiscussionInfoDto.summary,
                endDate = EndDate(onlineDiscussionInfoDto.endDate.toIsoLocalDate()),
            )

        @Serializable
        data class OnlineDiscussionInfoDto(
            @SerialName("endDate")
            val endDate: String,
        )
    }

    @Serializable
    @SerialName("OFFLINE")
    data class DiscussionDetailOfflineResponse(
        @SerialName("id")
        val id: Long,
        @SerialName("commonDiscussionInfo")
        val commonDiscussionInfoDto: CommonDiscussionInfoDto,
        @SerialName("offlineDiscussionInfo")
        val offlineDiscussionInfoDto: OfflineDiscussionInfoDto,
    ) : DiscussionDetailResponse {
        fun toDomain(): DiscussionDetail =
            OfflineDiscussionDetail(
                detailContent = DetailContent(
                    id = id,
                    title = commonDiscussionInfoDto.title,
                    author = commonDiscussionInfoDto.authorDto.toDomain(),
                    category = Track.valueOf(commonDiscussionInfoDto.category),
                    content = commonDiscussionInfoDto.content,
                    createdAt = commonDiscussionInfoDto.createdAt.toIsoLocalDateTime(),
                    likeCount = commonDiscussionInfoDto.likeCount,
                    modifiedAt = commonDiscussionInfoDto.modifiedAt.toIsoLocalDateTime(),
                ),
                summary = commonDiscussionInfoDto.summary,
                dateTimePeriod = DateTimePeriod(
                    startAt = offlineDiscussionInfoDto.startAt.toIsoLocalDateTime(),
                    endAt = offlineDiscussionInfoDto.endAt.toIsoLocalDateTime(),
                ),
                participantCapacity = ParticipantCapacity(
                    current = offlineDiscussionInfoDto.participantCount,
                    max = offlineDiscussionInfoDto.maxParticipantCount,
                ),
                place = offlineDiscussionInfoDto.place,
                participants = offlineDiscussionInfoDto.participantsDto.map { it.toDomain() },
            )

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
                fun toDomain(): Participant = Participant(id = id, name = name)
            }
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
                Author(id = id, nickname = name, profileImage = profileImageDto?.toDomain())

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
}
