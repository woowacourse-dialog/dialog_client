package com.on.dialog.discussiondetail.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.discussiondetail.impl.component.DiscussionDetailBody
import com.on.dialog.discussiondetail.impl.component.DiscussionDetailHeader
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel.AuthorUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.OfflineDiscussionDetailUiModel.ParticipantUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionStatusUiModel
import com.on.dialog.discussiondetail.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailIntent
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailState
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailViewModel
import com.on.dialog.model.common.Track
import com.on.dialog.ui.component.markdown.MarkdownEditor
import kotlinx.collections.immutable.persistentListOf
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
        onBookmarkClick = { viewModel.onIntent(DiscussionDetailIntent.ToggleBookmark(discussionId)) },
        onLikeClick = { viewModel.onIntent(DiscussionDetailIntent.ToggleLike(discussionId)) },
        onParticipateClick = { viewModel.onIntent(DiscussionDetailIntent.Participate(discussionId)) },
        onSummaryClick = { viewModel.onIntent(DiscussionDetailIntent.GenerateSummary(discussionId)) },
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
    onBookmarkClick: () -> Unit,
    onLikeClick: () -> Unit,
    onParticipateClick: () -> Unit,
    onSummaryClick: () -> Unit,
    content: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = DialogTheme.colorScheme.background)
    ) {
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
            DiscussionDetailContent(
                state = state,
                onLikeClick = onLikeClick,
                onSummaryClick = onSummaryClick,
                onBookmarkClick = onBookmarkClick,
                onParticipateClick = onParticipateClick,
            )
            CommentInputPlaceholder(
                text = content,
                onClick = onCommentInputClick,
                modifier = Modifier.padding(top = DialogTheme.spacing.medium),
            )
        }
    }
}

@Composable
private fun DiscussionDetailContent(
    state: DiscussionDetailState,
    onLikeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onSummaryClick: () -> Unit,
    onParticipateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val discussion = state.discussion ?: return

    Column(
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
        modifier = modifier.padding(PaddingValues(DialogTheme.spacing.large)),
    ) {
        DiscussionDetailHeader(
            discussion = discussion,
            onLikeClick = onLikeClick,
            onBookmarkClick = onBookmarkClick
        )
        DiscussionDetailBody(
            discussion = discussion,
            onSummaryClick = onSummaryClick,
            onParticipateClick = onParticipateClick
        )
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
                ),
                goBack = {},
                onCommentInputClick = {},
                onBookmarkClick = {},
                onLikeClick = {},
                onParticipateClick = {},
                onSummaryClick = {},
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
                        author = AuthorUiModel(0L, "크림", ""),
                        category = Track.ANDROID.toUiModel(),
                        content = "다이얼로그가 무슨 뜻인지 궁금합니다. ".repeat(15),
                        createdAt = "2023.03.01",
                        likeCount = 100,
                        modifiedAt = "2023.03.01",
                    ),
                    summary = null,
                    status = DiscussionStatusUiModel.IN_DISCUSSION,
                    endDate = "2026년 3월 10일",
                ),
            ),
            goBack = {},
            onCommentInputClick = {},
            onBookmarkClick = {},
            onLikeClick = {},
            onParticipateClick = {},
            onSummaryClick = {},
            content = "",
        )
    }
}
