package com.on.dialog.discussiondetail.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.designsystem.component.LoadingIndicator
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.util.drawFadingEdges
import com.on.dialog.discussiondetail.impl.component.CommentSection
import com.on.dialog.discussiondetail.impl.component.DiscussionDetailContent
import com.on.dialog.discussiondetail.impl.component.DiscussionDetailHeader
import com.on.dialog.discussiondetail.impl.component.DiscussionDetailTopAppBar
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel.AuthorUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.OfflineDiscussionDetailUiModel.ParticipantUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionStatusUiModel
import com.on.dialog.discussiondetail.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailEffect
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailIntent
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailState
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailViewModel
import com.on.dialog.model.common.Track
import com.on.dialog.ui.component.markdown.MarkdownEditor
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun DiscussionDetailScreen(
    discussionId: Long,
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DiscussionDetailViewModel = koinViewModel { parametersOf(discussionId) },
) {
    val snackbarDelegate = LocalSnackbarDelegate.current
    val uiState: DiscussionDetailState by viewModel.uiState.collectAsStateWithLifecycle()
    var showMarkdownEditor by rememberSaveable { mutableStateOf(false) }
    var targetCommentId by rememberSaveable { mutableStateOf<Long?>(null) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is DiscussionDetailEffect.ShowSnackbar -> snackbarDelegate.showSnackbar(
                    state = effect.state,
                    message = getString(effect.message),
                )

                DiscussionDetailEffect.NavigateHome -> goBack()
            }
        }
    }

    DiscussionDetailScreen(
        state = uiState,
        goBack = goBack,
        onCommentClick = {
            showMarkdownEditor = true
            targetCommentId = null
        },
        onReplyClick = { commentId ->
            showMarkdownEditor = true
            targetCommentId = commentId
        },
        onBookmarkClick = { viewModel.onIntent(DiscussionDetailIntent.ToggleBookmark) },
        onLikeClick = { viewModel.onIntent(DiscussionDetailIntent.ToggleLike) },
        onParticipateClick = { viewModel.onIntent(DiscussionDetailIntent.Participate) },
        onSummaryClick = { viewModel.onIntent(DiscussionDetailIntent.GenerateSummary) },
        onEditClick = { /* TODO: 수정 화면으로 이동 */ },
        onDeleteClick = { /* TODO: 삭제 확인 다이얼로그 표시 */ },
        modifier = modifier,
    )

    if (showMarkdownEditor) {
        MarkdownEditor(
            initialContent = "",
            onConfirm = { newContent: String ->
                showMarkdownEditor = false
                val intent = targetCommentId?.let { commentId ->
                    DiscussionDetailIntent.OnSubmitReply(commentId, newContent)
                } ?: DiscussionDetailIntent.OnSubmitComment(newContent)
                viewModel.onIntent(intent)
            },
            onExit = { showMarkdownEditor = false },
        )
    }
}

@Composable
private fun DiscussionDetailScreen(
    state: DiscussionDetailState,
    goBack: () -> Unit,
    onCommentClick: () -> Unit,
    onReplyClick: (commentId: Long) -> Unit,
    onBookmarkClick: () -> Unit,
    onLikeClick: () -> Unit,
    onParticipateClick: () -> Unit,
    onSummaryClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DialogTheme.colorScheme.background),
    ) {
        DiscussionDetailTopAppBar(
            showActions = state.isMyDiscussion,
            goBack = goBack,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick,
        )

        if (state.isLoading) {
            LoadingIndicator()
            return@Column
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .drawFadingEdges(scrollState)
                .verticalScroll(scrollState),
        ) {
            state.discussion?.let { discussion ->
                DiscussionDetailHeader(
                    discussion = discussion,
                    isBookmarked = state.isBookmarked,
                    isLiked = state.isLiked,
                    likeCount = state.likeCount,
                    onLikeClick = onLikeClick,
                    onBookmarkClick = onBookmarkClick,
                )

                DiscussionDetailContent(
                    content = discussion.detailContent.content,
                    summary = discussion.summary,
                    isMyDiscussion = state.isMyDiscussion,
                    isShowParticipateButton = state.isShowParticipateButton,
                    isShowSummary = state.isShowSummary,
                    isGeneratingSummary = state.isGeneratingSummary,
                    isParticipating = state.isParticipating,
                    onSummaryClick = onSummaryClick,
                    onParticipateClick = onParticipateClick,
                )
            }

            CommentSection(
                comments = state.comments,
                onCommentClick = onCommentClick,
                onReplyClick = onReplyClick,
            )
        }
    }
}

@ThemePreview
@Composable
private fun DiscussionDetailScreenOfflinePreview() {
    DialogTheme {
        Surface {
            DiscussionDetailScreen(
                state = DiscussionDetailState(
                    isLoading = false,
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
                        participantCapacity = "3/4",
                        place = "우아한테크코스",
                        participants = persistentListOf(
                            ParticipantUiModel(id = 0L, name = "다이스"),
                            ParticipantUiModel(id = 1L, name = "제리"),
                            ParticipantUiModel(id = 2L, name = "크림"),
                        ),
                        dateTimePeriod = "2023년 3월 1일 13시 ~ 15시",
                    ),
                    isMyDiscussion = true,
                ),
                goBack = {},
                onCommentClick = {},
                onReplyClick = {},
                onBookmarkClick = {},
                onLikeClick = {},
                onParticipateClick = {},
                onSummaryClick = {},
                onEditClick = {},
                onDeleteClick = {},
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
                isLoading = false,
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
                    status = DiscussionStatusUiModel.DISCUSSION_COMPLETE,
                    endDate = "2026년 3월 10일",
                ),
                isMyDiscussion = false,
            ),
            goBack = {},
            onCommentClick = {},
            onReplyClick = {},
            onBookmarkClick = {},
            onLikeClick = {},
            onParticipateClick = {},
            onSummaryClick = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}
