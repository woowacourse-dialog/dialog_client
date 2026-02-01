package com.on.dialog.feature.discussionlist.impl.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.on.dialog.core.common.extension.now
import com.on.dialog.feature.discussionlist.impl.model.DiscussionStatusUiModel.Companion.toUiModel
import com.on.dialog.feature.discussionlist.impl.model.DiscussionTypeUiModel.Companion.toUiModel
import com.on.dialog.feature.discussionlist.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.model.discussion.catalog.DiscussionCatalog
import com.on.dialog.model.discussion.catalog.OfflineDiscussionCatalog
import com.on.dialog.model.discussion.catalog.OnlineDiscussionCatalog
import com.on.dialog.model.discussion.content.DiscussionType
import com.on.dialog.ui.component.ChipCategory
import com.on.dialog.ui.mapper.toChipCategory
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

@Immutable
internal data class DiscussionUiModel(
    val id: Long,
    val title: String,
    val author: String,
    val track: TrackUiModel,
    val status: DiscussionStatusUiModel,
    val type: DiscussionTypeUiModel,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val commentCount: Int,
    val profileImage: String,
) {
    @Composable
    fun toChipCategories(): List<ChipCategory> = listOf(
        type.toDomain().toChipCategory(),
        track.toDomain().toChipCategory(),
    )

    companion object {
        @OptIn(ExperimentalTime::class)
        fun DiscussionCatalog.toUiModel(now: LocalDateTime = LocalDateTime.now()): DiscussionUiModel {
            val type = when (this) {
                is OnlineDiscussionCatalog -> DiscussionType.ONLINE
                is OfflineDiscussionCatalog -> DiscussionType.OFFLINE
            }

            return DiscussionUiModel(
                id = catalogContent.id,
                title = catalogContent.title,
                author = catalogContent.author,
                track = catalogContent.category.toUiModel(),
                status = status(now = now).toUiModel(),
                type = type.toUiModel(),
                createdAt = catalogContent.createdAt,
                modifiedAt = catalogContent.modifiedAt,
                commentCount = catalogContent.commentCount,
                profileImage = catalogContent.profileImage.customImageUri
                    ?: catalogContent.profileImage.basicImageUri,
            )
        }
    }
}
