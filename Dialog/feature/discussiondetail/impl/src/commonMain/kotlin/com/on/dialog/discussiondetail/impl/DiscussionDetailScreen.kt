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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.on.dialog.designsystem.component.DialogButton
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
    Box {
        Column(modifier = modifier.fillMaxSize()) {
            DialogTopAppBar(
                title = "",
                centerAligned = false,
                navigationIcon = {
                    DialogIconButton(onClick = goBack) {
                        Icon(imageVector = DialogIcons.ArrowBack, contentDescription = null)
                    }
                },
            )
            DiscussionDetailContent(state = state)

            CommentInputPlaceholder(
                text = content,
                onClick = onCommentInputClick,
                modifier = Modifier.padding(top = DialogTheme.spacing.medium),
            )
        }

        Box(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)) {
            DiscussionDetailBottomBar()
        }
    }
}

@Composable
private fun DiscussionDetailBottomBar(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().background(DialogTheme.colorScheme.secondaryContainer),
    ) {
        InteractionIndicator(icon = DialogIcons.Favorite, count = 0)
        DialogButton(text = "참여하기", onClick = {})
    }
}

@Composable
private fun DiscussionDetailContent(
    state: DiscussionDetailState,
    modifier: Modifier = Modifier,
) {
    val detailContent: DetailContentUiModel = state.discussion?.detailContent ?: return

    Column(
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.large),
        modifier = modifier.padding(PaddingValues(DialogTheme.spacing.large)),
    ) {
        DialogChipGroup(
            chips = state.discussion.toChipCategories(),
            onChipsChange = {},
        )

        Text(
            text = detailContent.title,
            style = DialogTheme.typography.titleLarge,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProfileImage(
                imageUrl = detailContent.author.imageUrl,
                contentDescription = "프로필 이미지",
                modifier = Modifier.size(40.dp),
            )
            Spacer(Modifier.width(DialogTheme.spacing.medium))
            Column(
                verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraSmall),
            ) {
                Text(
                    text = detailContent.author.nickname,
                    style = DialogTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = detailContent.modifiedAt.ifBlank { detailContent.createdAt },
                    style = DialogTheme.typography.bodyMedium,
                    color = DialogTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
            }
        }

        Text(text = detailContent.content, style = DialogTheme.typography.bodyMedium)
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
private fun DiscussionDetailOnlineContentPreview() {
    DialogTheme {
        Surface {
            DiscussionDetailContent(
                state = DiscussionDetailState(
                    discussion = DiscussionDetailUiModel.OnlineDiscussionDetailUiModel(
                        detailContent = DetailContentUiModel(
                            id = 0L,
                            title = "다이얼로그는 무슨 뜻인가요?",
                            author = DetailContentUiModel.AuthorUiModel(0L, "크림", ""),
                            category = Track.ANDROID.toUiModel(),
                            content = "다이얼로그가 무슨 뜻인지 궁금합니다.",
                            createdAt = "2023.03.01",
                            likeCount = 100,
                            modifiedAt = "2023.03.01",
                        ),
                        summary = "요약된 내용",
                        status = DiscussionStatusUiModel.IN_DISCUSSION,
                        endDate = "2026.03.10",
                    ),
                ),
            )
        }
    }
}

@Preview
@Composable
private fun DiscussionDetailOfflineContentPreview() {
    DialogTheme {
        Surface {
            DiscussionDetailContent(
                state = DiscussionDetailState(
                    discussion = DiscussionDetailUiModel.OfflineDiscussionDetailUiModel(
                        detailContent = DetailContentUiModel(
                            id = 0L,
                            title = "다이얼로그는 무슨 뜻인가요?",
                            author = DetailContentUiModel.AuthorUiModel(0L, "크림", ""),
                            category = Track.ANDROID.toUiModel(),
                            content = "다이얼로그가 무슨 뜻인지 궁금합니다.",
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
                                name = "크림",
                            ),
                        ),
                    ),
                ),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DiscussionDetailScreenPreview() {
    DialogTheme {
        DiscussionDetailScreen(
            state = DiscussionDetailState(
                DiscussionDetailUiModel.OnlineDiscussionDetailUiModel(
                    detailContent = DetailContentUiModel(
                        id = 0L,
                        title = "다이얼로그는 무슨 뜻인가요?",
                        author = DetailContentUiModel.AuthorUiModel(0L, "크림", ""),
                        category = Track.ANDROID.toUiModel(),
                        content = "다이얼로그가 무슨 뜻인지 궁금합니다.",
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
