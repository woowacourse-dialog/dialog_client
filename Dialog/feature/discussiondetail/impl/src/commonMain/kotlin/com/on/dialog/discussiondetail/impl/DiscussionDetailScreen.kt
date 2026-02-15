package com.on.dialog.discussiondetail.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionStatusUiModel
import com.on.dialog.discussiondetail.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailState
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailViewModel
import com.on.dialog.model.common.Track
import com.on.dialog.ui.component.DialogChipGroup
import com.on.dialog.ui.component.InteractionIndicator
import com.on.dialog.ui.component.ProfileImage
import com.on.dialog.ui.component.markdown.MarkdownEditor
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DiscussionDetailScreen(
    discussionId: Long,
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DiscussionDetailViewModel = koinViewModel(),
) {
    var commentContent by rememberSaveable { mutableStateOf("") }
    var showMarkdownEditor by rememberSaveable { mutableStateOf(false) }

    val uiState: DiscussionDetailState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchDiscussion(id = discussionId)
    }

    DiscussionDetailScreen(
        state = uiState,
        goBack = goBack,
        onCommentInputClick = { showMarkdownEditor = true },
        content = commentContent,
        modifier = modifier,
    )

    if (showMarkdownEditor) {
        MarkdownEditor(
            initialContent = commentContent,
            onConfirm = { newContent: String ->
                commentContent = newContent
                showMarkdownEditor = false
            },
            onExit = { showMarkdownEditor = false },
        )
    }
}

@Composable
private fun DiscussionDetailScreen(
    state: DiscussionDetailState,
    goBack: () -> Unit,
    onCommentInputClick: () -> Unit,
    content: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        DialogTopAppBar(
            title = "",
            navigationIcon = {
                DialogIconButton(onClick = goBack) {
                    Icon(imageVector = DialogIcons.ArrowBack, contentDescription = null)
                }
            },
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            DiscussionDetailContent(state = state)

            CommentInputPlaceholder(
                text = content,
                onClick = onCommentInputClick,
                modifier = Modifier.padding(top = DialogTheme.spacing.medium),
            )
        }

        if (state.discussion != null) {
            DiscussionDetailBottomBar(state.discussion)
        }
    }
}

@Composable
private fun DiscussionDetailBottomBar(
    discussion: DiscussionDetailUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(DialogTheme.colorScheme.primary.copy(alpha = 0.1f))
            .padding(PaddingValues(DialogTheme.spacing.extraSmall)),
    ) {
        DialogIconButton(
            onClick = {},
        ) {
            Icon(
                imageVector = DialogIcons.Bookmark,
                contentDescription = "북마크",
            )
        }

        when (discussion) {
            is DiscussionDetailUiModel.OfflineDiscussionDetailUiModel -> {
                DialogButton(text = "참여하기", onClick = {})
            }

            is DiscussionDetailUiModel.OnlineDiscussionDetailUiModel -> {
            }
        }
    }
}

@Composable
private fun DiscussionDetailContent(
    state: DiscussionDetailState,
    modifier: Modifier = Modifier,
) {
    val detailContent: DetailContentUiModel = state.discussion?.detailContent ?: return

    Column(
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraLarge),
        modifier = modifier.padding(PaddingValues(DialogTheme.spacing.large)),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            DialogChipGroup(
                chips = state.discussion.toChipCategories(),
                onChipsChange = {},
                modifier = Modifier.weight(1f),
            )
            InteractionIndicator(
                icon = DialogIcons.FavoriteBorder,
                contentDescription = "좋아요",
                count = detailContent.likeCount,
            )
        }
        Text(text = detailContent.title, style = DialogTheme.typography.titleLarge)
        ProfileSection(
            author = detailContent.author,
            modifiedAt = detailContent.modifiedAt,
            createdAt = detailContent.createdAt,
        )
        DiscussionInfoSection(
            discussion = state.discussion,
            modifier = Modifier.fillMaxWidth(),
        )
        Markdown(
            content = detailContent.content,
            colors = markdownColor(),
            typography = markdownTypography(),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ProfileSection(
    author: DetailContentUiModel.AuthorUiModel,
    modifiedAt: String,
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
                text = if (modifiedAt.isBlank()) {
                    "$createdAt 작성"
                } else {
                    "$modifiedAt 수정"
                },
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
    DialogCard {
        Column(
            verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small),
            modifier = modifier.fillMaxWidth(),
        ) {
            when (discussion) {
                is DiscussionDetailUiModel.OfflineDiscussionDetailUiModel -> {
                    IconTextRow(iconImage = DialogIcons.Place, text = discussion.place)
                    IconTextRow(
                        iconImage = DialogIcons.Calendar,
                        text = "${discussion.dateTimePeriod.startAt} ~ ${discussion.dateTimePeriod.endAt}",
                    )
                    Column {
                        IconTextRow(
                            iconImage = DialogIcons.Group,
                            text = discussion.participantCapacity,
                        )

                        Row {
                            discussion.participants.forEach {
                                Text(
                                    text = it.name,
                                    style = DialogTheme.typography.bodyMedium,
                                )
                                Spacer(modifier = Modifier.width(DialogTheme.spacing.small))
                            }
                        }
                    }
                }

                is DiscussionDetailUiModel.OnlineDiscussionDetailUiModel -> {
                    IconTextRow(
                        iconImage = DialogIcons.Calendar,
                        text = "${discussion.endDate} 종료",
                    )
                }
            }
        }
    }
}

@Composable
private fun IconTextRow(
    iconImage: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalContentColor provides DialogTheme.colorScheme.onSurfaceVariant,
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = iconImage,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
            Spacer(Modifier.width(DialogTheme.spacing.small))
            Text(
                text = text,
                style = DialogTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun CommentInputPlaceholder(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEmpty = text.isBlank()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = DialogTheme.spacing.large)
            .clip(DialogTheme.shapes.medium)
            .clickable(onClick = onClick)
            .background(
                color = DialogTheme.colorScheme.surfaceVariant,
                shape = DialogTheme.shapes.medium,
            ).padding(
                horizontal = DialogTheme.spacing.medium,
                vertical = DialogTheme.spacing.large,
            ),
    ) {
        Markdown(
            content = if (isEmpty) "댓글을 입력해 주세요" else text,
            colors = markdownColor(),
            typography = markdownTypography(),
            modifier = Modifier.wrapContentHeight(),
        )
    }
}

@Preview
@Composable
private fun DiscussionDetailScreenOfflinePreview() {
    DialogTheme {
        Surface {
            DiscussionDetailScreen(
                state = DiscussionDetailState(
                    discussion = DiscussionDetailUiModel.OfflineDiscussionDetailUiModel(
                        detailContent = DetailContentUiModel(
                            id = 0L,
                            title = "다이얼로그는 무슨 뜻인가요?",
                            author = DetailContentUiModel.AuthorUiModel(0L, "크림", ""),
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
                        participants = listOf(
                            DiscussionDetailUiModel.OfflineDiscussionDetailUiModel.ParticipantUiModel(
                                id = 0L,
                                name = "다이스",
                            ),
                            DiscussionDetailUiModel.OfflineDiscussionDetailUiModel.ParticipantUiModel(
                                id = 1L,
                                name = "제리",
                            ),
                            DiscussionDetailUiModel.OfflineDiscussionDetailUiModel.ParticipantUiModel(
                                id = 2L,
                                name = "크림",
                            ),
                        ),
                        dateTimePeriod = DiscussionDetailUiModel.OfflineDiscussionDetailUiModel.DateTimePeriodUiModel(
                            startAt = "2023년 3월 1일 13시",
                            endAt = "2023년 3월 1일 15시",
                        ),
                    ),
                ),
                goBack = {},
                onCommentInputClick = {},
                content = "",
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DiscussionDetailScreenOnlinePreview() {
    DialogTheme {
        DiscussionDetailScreen(
            state = DiscussionDetailState(
                discussion = DiscussionDetailUiModel.OnlineDiscussionDetailUiModel(
                    detailContent = DetailContentUiModel(
                        id = 0L,
                        title = "다이얼로그는 무슨 뜻인가요?",
                        author = DetailContentUiModel.AuthorUiModel(0L, "크림", ""),
                        category = Track.ANDROID.toUiModel(),
                        content = "다이얼로그가 무슨 뜻인지 궁금합니다. ".repeat(15),
                        createdAt = "2023.03.01",
                        likeCount = 100,
                        modifiedAt = "2023.03.01",
                    ),
                    summary = "요약된 내용",
                    status = DiscussionStatusUiModel.IN_DISCUSSION,
                    endDate = "2026.03.10",
                ),
            ),
            goBack = {},
            onCommentInputClick = {},
            content = "",
        )
    }
}
