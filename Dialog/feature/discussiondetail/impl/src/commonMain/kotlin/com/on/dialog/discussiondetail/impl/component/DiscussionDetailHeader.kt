package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

    Column {
        DiscussionActionBar(
            chips = discussion.toChipCategories(),
            likeCount = likeCount,
            isBookmarked = isBookmarked,
            isLiked = isLiked,
            onLikeClick = onLikeClick,
            onBookmarkClick = onBookmarkClick,
            modifier = modifier,
        )
        Spacer(modifier = Modifier.height(DialogTheme.spacing.extraSmall))

        Text(text = detailContent.title, style = DialogTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))

        ProfileSection(
            author = detailContent.author,
            createdAt = detailContent.createdAt,
        )
        Spacer(modifier = Modifier.height(DialogTheme.spacing.small))

        DiscussionInfoSection(
            discussion = discussion,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(DialogTheme.spacing.extraSmall))
        HorizontalDivider()
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
        modifier = modifier.fillMaxWidth(),
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
            contentDescription = "좋아요",
            count = likeCount,
            onClick = onLikeClick,
        )
        DialogIconButton(onClick = onBookmarkClick) {
            Icon(
                imageVector = if (isBookmarked) DialogIcons.Bookmark else DialogIcons.BookmarkBorder,
                contentDescription = "북마크",
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        ProfileImage(
            imageUrl = author.imageUrl,
            contentDescription = "프로필 이미지",
            modifier = Modifier.size(40.dp),
        )
        Spacer(Modifier.width(DialogTheme.spacing.medium))
        Column(
            verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraSmall),
        ) {
            Text(
                text = author.nickname,
                style = DialogTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "$createdAt 작성",
                style = DialogTheme.typography.bodyMedium,
                color = DialogTheme.colorScheme.onSurface.copy(alpha = 0.7f),
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
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small),
        modifier = modifier.fillMaxWidth().padding(DialogTheme.spacing.small),
    ) {
        when (discussion) {
            is DiscussionDetailUiModel.OfflineDiscussionDetailUiModel -> {
                IconTextRow(iconImage = DialogIcons.Place, text = discussion.place)
                IconTextRow(
                    iconImage = DialogIcons.Calendar,
                    text = discussion.dateTimePeriod,
                )
                ParticipantList(
                    capacity = discussion.participantCapacity,
                    participants = discussion.participants,
                )
            }

            is DiscussionDetailUiModel.OnlineDiscussionDetailUiModel -> {
                IconTextRow(
                    iconImage = DialogIcons.Calendar,
                    text = stringResource(Res.string.end_date_format, discussion.endDate),
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
    Column(modifier = modifier) {
        IconTextRow(
            iconImage = DialogIcons.Group,
            text = capacity,
        )
        Row {
            participants.forEach {
                Text(text = it.name, style = DialogTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.width(DialogTheme.spacing.small))
            }
        }
    }
}

@ThemePreview
@Composable
private fun DiscussionDetailHeaderPreview() {
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
                    summary = "요약된 내용",
                    status = DiscussionStatusUiModel.IN_DISCUSSION,
                    participantCapacity = "1/4",
                    place = "우아한테크코스",
                    participants = persistentListOf(
                        ParticipantUiModel(id = 0L, name = "다이스"),
                        ParticipantUiModel(id = 1L, name = "제리"),
                        ParticipantUiModel(id = 2L, name = "크림"),
                    ),
                    dateTimePeriod = "2023년 3월 1일 13시 ~ 15시",
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
