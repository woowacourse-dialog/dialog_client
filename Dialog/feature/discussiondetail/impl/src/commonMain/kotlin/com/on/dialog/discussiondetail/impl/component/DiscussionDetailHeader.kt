package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogHorizontalDivider
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel.AuthorUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.OfflineDiscussionDetailUiModel.ParticipantUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionStatusUiModel
import com.on.dialog.discussiondetail.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.model.common.Track
import com.on.dialog.ui.component.ChipCategory
import com.on.dialog.ui.component.DialogChipGroup
import com.on.dialog.ui.component.InteractionIndicator
import com.on.dialog.ui.component.ProfileImage
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.end_date_format
import dialog.feature.discussiondetail.impl.generated.resources.header_bookmark_content_description
import dialog.feature.discussiondetail.impl.generated.resources.header_created_at
import dialog.feature.discussiondetail.impl.generated.resources.header_info
import dialog.feature.discussiondetail.impl.generated.resources.header_like_content_description
import dialog.feature.discussiondetail.impl.generated.resources.header_participants_format
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionDetailHeader(
    discussion: DiscussionDetailUiModel,
    isBookmarked: Boolean,
    isLiked: Boolean,
    likeCount: Int,
    onLikeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val detailContent: DetailContentUiModel = discussion.detailContent

    Column(modifier = modifier) {
        DiscussionActionBar(
            chips = discussion.toChipCategories(),
            likeCount = likeCount,
            isBookmarked = isBookmarked,
            isLiked = isLiked,
            onLikeClick = onLikeClick,
            onBookmarkClick = onBookmarkClick,
        )
        Spacer(modifier = Modifier.height(DialogTheme.spacing.extraSmall))

        Text(
            text = detailContent.title,
            style = DialogTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth().padding(horizontal = DialogTheme.spacing.large),
        )
        Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
        DialogHorizontalDivider()
        ProfileSection(
            author = detailContent.author,
            createdAt = detailContent.createdAt,
        )
        DialogHorizontalDivider()
        DiscussionInfoSection(discussion = discussion)
        DialogHorizontalDivider()
    }
}

@Composable
private fun DiscussionActionBar(
    chips: ImmutableList<ChipCategory>,
    likeCount: Int,
    isBookmarked: Boolean,
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = DialogTheme.spacing.large, end = DialogTheme.spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        DialogChipGroup(
            chips = chips,
            onChipsChange = {},
            modifier = Modifier.weight(1f),
        )
        InteractionIndicator(
            icon = if (isLiked) DialogIcons.Favorite else DialogIcons.FavoriteBorder,
            contentDescription = stringResource(Res.string.header_like_content_description),
            count = likeCount,
            onClick = onLikeClick,
        )
        DialogIconButton(onClick = onBookmarkClick) {
            Icon(
                imageVector = if (isBookmarked) DialogIcons.Bookmark else DialogIcons.BookmarkBorder,
                contentDescription = stringResource(Res.string.header_bookmark_content_description),
            )
        }
    }
}

@Composable
private fun ProfileSection(
    author: AuthorUiModel,
    createdAt: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = DialogTheme.spacing.small, horizontal = DialogTheme.spacing.large),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProfileImage(
                imageUrl = author.imageUrl,
                modifier = Modifier.size(28.dp),
            )
            Spacer(Modifier.width(DialogTheme.spacing.small))
            Text(
                text = author.nickname,
                style = DialogTheme.typography.titleMedium,
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = stringResource(Res.string.header_created_at),
                style = DialogTheme.typography.titleSmall,
            )
            Text(
                text = createdAt,
                style = DialogTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun DiscussionInfoSection(
    discussion: DiscussionDetailUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = DialogTheme.spacing.small),
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small),
    ) {
        when (discussion) {
            is DiscussionDetailUiModel.OfflineDiscussionDetailUiModel -> {
                InfoSection(
                    formattedDateTime = discussion.dateTimePeriod,
                    discussionPlace = discussion.place,
                )
                DialogHorizontalDivider()

                ParticipantList(
                    capacity = discussion.participantCapacity,
                    participants = discussion.participants,
                )
            }

            is DiscussionDetailUiModel.OnlineDiscussionDetailUiModel -> {
                InfoSection(
                    formattedDateTime = stringResource(
                        Res.string.end_date_format,
                        discussion.endDate,
                    ),
                    discussionPlace = null,
                )
            }
        }
    }
}

@Composable
private fun InfoSection(
    formattedDateTime: String?,
    discussionPlace: String?,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = DialogTheme.spacing.large)) {
        IconTextRow(
            iconImage = DialogIcons.Info,
            text = stringResource(Res.string.header_info),
            textStyle = DialogTheme.typography.titleSmall,
        )

        Spacer(modifier = Modifier.height(DialogTheme.spacing.small))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small),
            verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small),
        ) {
            formattedDateTime?.let { text ->
                MetaChip(
                    text = text,
                    iconImage = DialogIcons.Calendar,
                )
            }
            discussionPlace?.let { place ->
                MetaChip(
                    text = place,
                    iconImage = DialogIcons.Place,
                )
            }
        }
    }
}

@Composable
private fun ParticipantList(
    capacity: String,
    participants: ImmutableList<ParticipantUiModel>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = DialogTheme.spacing.large),
    ) {
        IconTextRow(
            iconImage = DialogIcons.Group,
            text = stringResource(Res.string.header_participants_format, capacity),
            textStyle = DialogTheme.typography.titleSmall,
        )

        Spacer(modifier = Modifier.height(DialogTheme.spacing.small))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small),
        ) {
            participants.forEach { participant -> MetaChip(text = participant.name) }
        }
    }
}

@ThemePreview
@Composable
private fun OnlineDiscussionDetailHeaderPreview() {
    DialogTheme {
        Surface {
            DiscussionDetailHeader(
                discussion = DiscussionDetailUiModel.OnlineDiscussionDetailUiModel(
                    detailContent = DetailContentUiModel(
                        id = 0L,
                        title = "다이얼로그는 무슨 뜻인가요?",
                        author = AuthorUiModel(0L, "크림", ""),
                        category = Track.ANDROID.toUiModel(),
                        content = "다이얼로그가 무슨 뜻인지 궁금합니다. ".repeat(15),
                        createdAt = "2023.03.01",
                        likeCount = 100,
                        modifiedAt = "2023.03.01",
                    ),
                    summary = "요약된 내용",
                    status = DiscussionStatusUiModel.IN_DISCUSSION,
                    endDate = "2026년 3월 28일",
                ),
                isBookmarked = false,
                isLiked = true,
                likeCount = 100,
                onLikeClick = {},
                onBookmarkClick = {},
            )
        }
    }
}

@ThemePreview
@Composable
private fun OfflineDiscussionDetailHeaderPreview() {
    DialogTheme {
        Surface {
            DiscussionDetailHeader(
                discussion = DiscussionDetailUiModel.OfflineDiscussionDetailUiModel(
                    detailContent = DetailContentUiModel(
                        id = 0L,
                        title = "다이얼로그는 무슨 뜻인가요?",
                        author = AuthorUiModel(0L, "크림", ""),
                        category = Track.ANDROID.toUiModel(),
                        content = "다이얼로그가 무슨 뜻인지 궁금합니다. ".repeat(15),
                        createdAt = "2023.03.01",
                        likeCount = 100,
                        modifiedAt = "2023.03.01",
                    ),
                    status = DiscussionStatusUiModel.IN_DISCUSSION,
                    participantCapacity = "3/4",
                    place = "우아한테크코스",
                    participants = persistentListOf(
                        ParticipantUiModel(id = 0L, name = "다이스"),
                        ParticipantUiModel(id = 1L, name = "제리"),
                        ParticipantUiModel(id = 2L, name = "크림"),
                    ),
                    dateTimePeriod = "2023년 3월 1일 13시 15분 ~ 15시 15분",
                ),
                isBookmarked = false,
                isLiked = true,
                likeCount = 100,
                onLikeClick = {},
                onBookmarkClick = {},
            )
        }
    }
}
