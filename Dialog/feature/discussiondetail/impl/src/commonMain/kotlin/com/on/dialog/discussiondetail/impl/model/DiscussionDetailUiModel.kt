package com.on.dialog.discussiondetail.impl.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.on.dialog.core.common.extension.now
import com.on.dialog.discussiondetail.impl.extensions.periodToKoreanString
import com.on.dialog.discussiondetail.impl.extensions.toKoreanString
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel.Companion.toUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.OfflineDiscussionDetailUiModel.Companion.toOfflineUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.OfflineDiscussionDetailUiModel.ParticipantUiModel.Companion.toUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.OnlineDiscussionDetailUiModel.Companion.toOnlineUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionStatusUiModel.Companion.toUiModel
import com.on.dialog.model.discussion.content.DiscussionType
import com.on.dialog.model.discussion.detail.DiscussionDetail
import com.on.dialog.model.discussion.detail.OfflineDiscussionDetail
import com.on.dialog.model.discussion.detail.OnlineDiscussionDetail
import com.on.dialog.model.discussion.offline.Participant
import com.on.dialog.ui.component.ChipCategory
import com.on.dialog.ui.mapper.toChipCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

@Immutable
sealed interface DiscussionDetailUiModel {
    val detailContent: DetailContentUiModel
    val summary: String?
    val status: DiscussionStatusUiModel

    @Composable
    fun toChipCategories(): ImmutableList<ChipCategory>

    @Immutable
    data class OfflineDiscussionDetailUiModel(
        override val detailContent: DetailContentUiModel,
        override val summary: String?,
        override val status: DiscussionStatusUiModel,
        val dateTimePeriod: String,
        val participantCapacity: String,
        val place: String,
        val participants: ImmutableList<ParticipantUiModel>,
    ) : DiscussionDetailUiModel {
        @Composable
        override fun toChipCategories(): ImmutableList<ChipCategory> =
            persistentListOf(
                DiscussionType.OFFLINE.toChipCategory(),
                detailContent.category.toDomain().toChipCategory(),
            )

        @Immutable
        data class ParticipantUiModel(
            val id: Long,
            val name: String,
        ) {
            companion object {
                fun Participant.toUiModel() = ParticipantUiModel(id = id, name = name)
            }
        }

        companion object {
            @OptIn(ExperimentalTime::class)
            fun OfflineDiscussionDetail.toOfflineUiModel(
                now: LocalDateTime = LocalDateTime.now(),
            ): OfflineDiscussionDetailUiModel = OfflineDiscussionDetailUiModel(
                detailContent = detailContent.toUiModel(),
                summary = summary,
                dateTimePeriod = periodToKoreanString(dateTimePeriod.startAt, dateTimePeriod.endAt),
                participantCapacity = "${participantCapacity.current}/${participantCapacity.max}",
                place = place,
                participants = participants.map { it.toUiModel() }.toImmutableList(),
                status = status(now).toUiModel(),
            )
        }
    }

    @Immutable
    data class OnlineDiscussionDetailUiModel(
        override val detailContent: DetailContentUiModel,
        override val summary: String?,
        override val status: DiscussionStatusUiModel,
        val endDate: String,
    ) : DiscussionDetailUiModel {
        @Composable
        override fun toChipCategories(): ImmutableList<ChipCategory> =
            persistentListOf(
                DiscussionType.ONLINE.toChipCategory(),
                detailContent.category.toDomain().toChipCategory(),
            )

        companion object {
            @OptIn(ExperimentalTime::class)
            fun OnlineDiscussionDetail.toOnlineUiModel(
                now: LocalDateTime = LocalDateTime.now(),
            ): OnlineDiscussionDetailUiModel = OnlineDiscussionDetailUiModel(
                detailContent = detailContent.toUiModel(),
                summary = summary,
                endDate = endDate.endDate.toKoreanString(),
                status = status(now).toUiModel(),
            )
        }
    }

    companion object {
        fun DiscussionDetail.toUiModel() = when (this) {
            is OfflineDiscussionDetail -> toOfflineUiModel()
            is OnlineDiscussionDetail -> toOnlineUiModel()
        }
    }
}
