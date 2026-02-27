package com.on.dialog.feature.discussionlist.impl.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.on.dialog.core.common.extension.now
import com.on.dialog.feature.discussionlist.impl.model.DiscussionStatusUiModel.Companion.toUiModel
import com.on.dialog.feature.discussionlist.impl.model.DiscussionUiModel.OfflineDiscussionUiModel.Companion.toOfflineUiModel
import com.on.dialog.feature.discussionlist.impl.model.DiscussionUiModel.OnlineDiscussionUiModel.Companion.toOnlineUiModel
import com.on.dialog.feature.discussionlist.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.model.discussion.catalog.DiscussionCatalog
import com.on.dialog.model.discussion.catalog.OfflineDiscussionCatalog
import com.on.dialog.model.discussion.catalog.OnlineDiscussionCatalog
import com.on.dialog.model.discussion.content.DiscussionType
import com.on.dialog.ui.component.ChipCategory
import com.on.dialog.ui.mapper.toChipCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

@Immutable
internal sealed interface DiscussionUiModel {
    val id: Long
    val title: String
    val author: String
    val track: TrackUiModel
    val status: DiscussionStatusUiModel
    val commentCount: Int
    val period: String
    val type: DiscussionTypeUiModel

    @Composable
    fun toChipCategories(): ImmutableList<ChipCategory>

    @Immutable
    data class OnlineDiscussionUiModel(
        override val id: Long,
        override val title: String,
        override val author: String,
        override val track: TrackUiModel,
        override val status: DiscussionStatusUiModel,
        override val commentCount: Int,
        override val period: String,
        override val type: DiscussionTypeUiModel = DiscussionTypeUiModel.ONLINE,
    ) : DiscussionUiModel {
        @Composable
        override fun toChipCategories(): ImmutableList<ChipCategory> =
            persistentListOf(
                DiscussionType.ONLINE.toChipCategory(),
                track.toDomain().toChipCategory(),
            )

        companion object {
            @OptIn(ExperimentalTime::class)
            fun OnlineDiscussionCatalog.toOnlineUiModel(now: LocalDateTime = LocalDateTime.now()): OnlineDiscussionUiModel =
                OnlineDiscussionUiModel(
                    id = catalogContent.id,
                    title = catalogContent.title,
                    author = catalogContent.author,
                    track = catalogContent.category.toUiModel(),
                    status = status(now = now).toUiModel(),
                    commentCount = catalogContent.commentCount,
                    period = "~ ${endDate.endDate}".replace("-", "."),
                )
        }
    }

    @Immutable
    data class OfflineDiscussionUiModel(
        override val id: Long,
        override val title: String,
        override val author: String,
        override val track: TrackUiModel,
        override val status: DiscussionStatusUiModel,
        override val commentCount: Int,
        override val period: String,
        override val type: DiscussionTypeUiModel = DiscussionTypeUiModel.OFFLINE,
        val participantCapacity: String,
        val place: String,
    ) : DiscussionUiModel {
        @Composable
        override fun toChipCategories(): ImmutableList<ChipCategory> = persistentListOf(
            DiscussionType.OFFLINE.toChipCategory(),
            track.toDomain().toChipCategory(),
        )

        companion object {
            @OptIn(ExperimentalTime::class)
            fun OfflineDiscussionCatalog.toOfflineUiModel(now: LocalDateTime = LocalDateTime.now()): OfflineDiscussionUiModel =
                OfflineDiscussionUiModel(
                    id = catalogContent.id,
                    title = catalogContent.title,
                    author = catalogContent.author,
                    track = catalogContent.category.toUiModel(),
                    status = status(now = now).toUiModel(),
                    commentCount = catalogContent.commentCount,
                    participantCapacity = "${participantCapacity.current}/${participantCapacity.max}",
                    place = place,
                    period = "${dateTimePeriod.startAt.date} ~ ${dateTimePeriod.endAt.date}".replace(
                        "-",
                        ".",
                    ),
                )
        }
    }

    companion object {
        @OptIn(ExperimentalTime::class)
        fun DiscussionCatalog.toUiModel(
            now: LocalDateTime = LocalDateTime.now(),
        ): DiscussionUiModel =
            when (this) {
                is OnlineDiscussionCatalog -> toOnlineUiModel(now = now)
                is OfflineDiscussionCatalog -> toOfflineUiModel(now = now)
            }
    }
}
