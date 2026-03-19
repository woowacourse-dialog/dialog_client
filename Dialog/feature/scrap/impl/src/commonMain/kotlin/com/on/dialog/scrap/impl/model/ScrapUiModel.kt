package com.on.dialog.scrap.impl.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.on.dialog.core.common.extension.now
import com.on.dialog.model.common.Track
import com.on.dialog.model.discussion.content.DiscussionType
import com.on.dialog.model.discussion.scrap.OfflineScrapCatalog
import com.on.dialog.model.discussion.scrap.OnlineScrapCatalog
import com.on.dialog.model.discussion.scrap.ScrapCatalog
import com.on.dialog.scrap.impl.model.DiscussionStatusUiModel.Companion.toUiModel
import com.on.dialog.scrap.impl.model.ScrapUiModel.OfflineScrapUiModel.Companion.toOfflineUiModel
import com.on.dialog.scrap.impl.model.ScrapUiModel.OnlineScrapUiModel.Companion.toOnlineUiModel
import com.on.dialog.ui.component.ChipCategory
import com.on.dialog.ui.mapper.toChipCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

@Immutable
internal sealed interface ScrapUiModel {
    val id: Long
    val title: String
    val author: String
    val track: Track
    val status: DiscussionStatusUiModel
    val commentCount: Int
    val period: String

    @Composable
    fun toChipCategories(): ImmutableList<ChipCategory>

    @Immutable
    data class OnlineScrapUiModel(
        override val id: Long,
        override val title: String,
        override val author: String,
        override val track: Track,
        override val status: DiscussionStatusUiModel,
        override val commentCount: Int,
        override val period: String,
    ) : ScrapUiModel {
        @Composable
        override fun toChipCategories(): ImmutableList<ChipCategory> =
            persistentListOf(
                DiscussionType.ONLINE.toChipCategory(),
                status.toDomain().toChipCategory(),
                track.toChipCategory(),
            )

        companion object {
            @OptIn(ExperimentalTime::class)
            fun OnlineScrapCatalog.toOnlineUiModel(now: LocalDateTime = LocalDateTime.now()): OnlineScrapUiModel =
                OnlineScrapUiModel(
                    id = catalogContent.id,
                    title = catalogContent.title,
                    author = catalogContent.author,
                    track = catalogContent.category,
                    status = status(now = now).toUiModel(),
                    commentCount = catalogContent.commentCount,
                    period = "~ ${endDate.endDate}".replace("-", "."),
                )
        }
    }

    @Immutable
    data class OfflineScrapUiModel(
        override val id: Long,
        override val title: String,
        override val author: String,
        override val track: Track,
        override val status: DiscussionStatusUiModel,
        override val commentCount: Int,
        override val period: String,
        val participantCapacity: String,
        val place: String,
    ) : ScrapUiModel {
        @Composable
        override fun toChipCategories(): ImmutableList<ChipCategory> = persistentListOf(
            DiscussionType.OFFLINE.toChipCategory(),
            status.toDomain().toChipCategory(),
            track.toChipCategory(),
        )

        companion object {
            @OptIn(ExperimentalTime::class)
            fun OfflineScrapCatalog.toOfflineUiModel(now: LocalDateTime = LocalDateTime.now()): OfflineScrapUiModel =
                OfflineScrapUiModel(
                    id = catalogContent.id,
                    title = catalogContent.title,
                    author = catalogContent.author,
                    track = catalogContent.category,
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
        fun ScrapCatalog.toUiModel(
            now: LocalDateTime = LocalDateTime.now(),
        ): ScrapUiModel =
            when (this) {
                is OfflineScrapCatalog -> toOfflineUiModel(now = now)
                is OnlineScrapCatalog -> toOnlineUiModel(now = now)
            }
    }
}
