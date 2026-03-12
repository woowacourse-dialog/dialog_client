package com.on.dialog.discussiondetail.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.component.LoadingIndicator
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.discussiondetail.impl.component.DiscussionDetailAppBar
import com.on.dialog.discussiondetail.impl.component.DiscussionDetailMainSection
import com.on.dialog.discussiondetail.impl.component.DiscussionReportReasonDialog
import com.on.dialog.discussiondetail.impl.model.CommentType
import com.on.dialog.discussiondetail.impl.model.DeleteType
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel.AuthorUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailOverlay
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.OfflineDiscussionDetailUiModel.ParticipantUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionStatusUiModel
import com.on.dialog.discussiondetail.impl.model.ReportType
import com.on.dialog.discussiondetail.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailEffect
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailIntent
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailState
import com.on.dialog.discussiondetail.impl.viewmodel.DiscussionDetailViewModel
import com.on.dialog.model.common.Track
import com.on.dialog.ui.component.DecisionDialog
import com.on.dialog.ui.component.markdown.MarkdownEditor
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.action_cancel
import dialog.feature.discussiondetail.impl.generated.resources.action_delete
import dialog.feature.discussiondetail.impl.generated.resources.comment_delete_write
import dialog.feature.discussiondetail.impl.generated.resources.discussion_delete_confirm
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
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
    var activeOverlay: DiscussionDetailOverlay? by rememberSaveable { mutableStateOf(null) }

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

    DiscussionDetailScreenScaffold(
        state = uiState,
        goBack = goBack,
        onCommentClick = { activeOverlay = DiscussionDetailOverlay.Comment(CommentType.Comment) },
        onReplyClick = { commentId ->
            activeOverlay =
                DiscussionDetailOverlay.Comment(CommentType.Reply(commentId = commentId))
        },
        onCommentEditClick = { commentId, content ->
            activeOverlay = DiscussionDetailOverlay.Comment(
                CommentType.Edit(commentId = commentId, originalContent = content),
            )
        },
        onCommentDeleteClick = { commentId ->
            activeOverlay =
                DiscussionDetailOverlay.Delete(DeleteType.Comment(commentId = commentId))
        },
        onCommentReportClick = { commentId ->
            activeOverlay =
                DiscussionDetailOverlay.Report(ReportType.Comment(commentId = commentId))
        },
        onBookmarkClick = { viewModel.onIntent(DiscussionDetailIntent.ToggleBookmark) },
        onLikeClick = { viewModel.onIntent(DiscussionDetailIntent.ToggleLike) },
        onParticipateClick = { viewModel.onIntent(DiscussionDetailIntent.Participate) },
        onSummaryClick = { viewModel.onIntent(DiscussionDetailIntent.GenerateSummary) },
        onEditClick = { /* TODO: 수정 화면으로 이동 */ },
        onDeleteClick = { activeOverlay = DiscussionDetailOverlay.Delete(DeleteType.Discussion) },
        onReportClick = { activeOverlay = DiscussionDetailOverlay.Report(ReportType.Discussion) },
        modifier = modifier,
    )

    DiscussionDetailOverlayHost(
        activeOverlay = activeOverlay,
        onDismiss = { activeOverlay = null },
        onIntent = viewModel::onIntent,
    )
}

@Composable
private fun DiscussionDetailMarkdownEditor(
    type: CommentType,
    onConfirm: (intent: DiscussionDetailIntent) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MarkdownEditor(
        modifier = modifier,
        initialContent = when (type) {
            is CommentType.Edit -> type.originalContent
            else -> ""
        },
        onConfirm = { newContent: String ->
            val intent = when (type) {
                CommentType.Comment -> DiscussionDetailIntent.OnSubmitComment(content = newContent)

                is CommentType.Reply -> DiscussionDetailIntent.OnSubmitReply(
                    commentId = type.commentId,
                    content = newContent,
                )

                is CommentType.Edit -> DiscussionDetailIntent.OnEditComment(
                    commentId = type.commentId,
                    content = newContent,
                )
            }
            onConfirm(intent)
        },
        onExit = onExit,
    )
}

@Composable
private fun DiscussionDetailOverlayHost(
    activeOverlay: DiscussionDetailOverlay?,
    onDismiss: () -> Unit,
    onIntent: (DiscussionDetailIntent) -> Unit,
) {
    when (activeOverlay) {
        is DiscussionDetailOverlay.Comment -> {
            DiscussionDetailMarkdownEditor(
                type = activeOverlay.type,
                onConfirm = {
                    onDismiss()
                    onIntent(it)
                },
                onExit = onDismiss,
            )
        }

        is DiscussionDetailOverlay.Delete -> {
            DecisionDialog(
                contentText = stringResource(Res.string.discussion_delete_confirm),
                confirmText = when (activeOverlay.type) {
                    DeleteType.Discussion -> stringResource(Res.string.action_delete)
                    is DeleteType.Comment -> stringResource(Res.string.comment_delete_write)
                },
                onConfirm = {
                    onDismiss()
                    when (val type = activeOverlay.type) {
                        DeleteType.Discussion -> onIntent(DiscussionDetailIntent.OnDeleteDiscussion)

                        is DeleteType.Comment -> onIntent(
                            DiscussionDetailIntent.OnDeleteComment(commentId = type.commentId),
                        )
                    }
                },
                confirmButtonStyle = DialogButtonStyle.Error,
                dismissText = stringResource(Res.string.action_cancel),
                onDismiss = onDismiss,
            )
        }

        is DiscussionDetailOverlay.Report -> {
            DiscussionReportReasonDialog(
                onDismiss = onDismiss,
                onConfirm = { reason ->
                    onDismiss()
                    when (val type = activeOverlay.type) {
                        ReportType.Discussion -> onIntent(
                            DiscussionDetailIntent.OnReportDiscussion(reason = reason),
                        )

                        is ReportType.Comment -> onIntent(
                            DiscussionDetailIntent.OnReportComment(
                                commentId = type.commentId,
                                reason = reason,
                            ),
                        )
                    }
                },
            )
        }

        null -> {
            Unit
        }
    }
}

@Composable
private fun DiscussionDetailScreenScaffold(
    state: DiscussionDetailState,
    goBack: () -> Unit,
    onCommentClick: () -> Unit,
    onReplyClick: (commentId: Long) -> Unit,
    onCommentEditClick: (commentId: Long, content: String) -> Unit,
    onCommentDeleteClick: (commentId: Long) -> Unit,
    onCommentReportClick: (commentId: Long) -> Unit,
    onBookmarkClick: () -> Unit,
    onLikeClick: () -> Unit,
    onParticipateClick: () -> Unit,
    onSummaryClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onReportClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        DiscussionDetailAppBar(
            isMyDiscussion = state.isMyDiscussion,
            goBack = goBack,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick,
            onReportClick = onReportClick,
            onBookmarkClick = onBookmarkClick,
            onLikeClick = onLikeClick,
            isLiked = state.isLiked,
            isBookmarked = state.isBookmarked,
        )

        if (state.isLoading) {
            LoadingIndicator()
            return@Column
        }
        DiscussionDetailMainSection(
            discussion = state.discussion,
            comments = state.comments,
            totalCommentCount = state.totalCommentCount,
            isMyDiscussion = state.isMyDiscussion,
            isShowParticipateButton = state.isShowParticipateButton,
            isShowSummary = state.isShowSummary,
            isGeneratingSummary = state.isGeneratingSummary,
            onParticipateClick = onParticipateClick,
            onSummaryClick = onSummaryClick,
            onCommentClick = onCommentClick,
            onReplyClick = onReplyClick,
            onCommentEditClick = onCommentEditClick,
            onCommentDeleteClick = onCommentDeleteClick,
            onCommentReportClick = onCommentReportClick,
        )
    }
}

@ThemePreview
@Composable
private fun DiscussionDetailScreenOfflinePreview() {
    DialogTheme {
        Surface {
            DiscussionDetailScreenScaffold(
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
                        status = DiscussionStatusUiModel.IN_DISCUSSION,
                        participantCapacity = "3/4",
                        place = "우아한테크코스",
                        participants = persistentListOf(
                            ParticipantUiModel(id = 0L, name = "다이스"),
                            ParticipantUiModel(id = 1L, name = "제리"),
                            ParticipantUiModel(id = 2L, name = "크림"),
                        ),
                        isParticipating = true,
                        dateTimePeriod = "2023년 3월 1일 13시 15분 ~ 15시 15분",
                    ),
                    isMyDiscussion = true,
                ),
                goBack = {},
                onCommentClick = {},
                onReplyClick = {},
                onCommentEditClick = { _, _ -> },
                onCommentDeleteClick = {},
                onCommentReportClick = {},
                onBookmarkClick = {},
                onLikeClick = {},
                onParticipateClick = {},
                onSummaryClick = {},
                onEditClick = {},
                onDeleteClick = {},
                onReportClick = {},
            )
        }
    }
}

@ThemePreview
@Composable
private fun DiscussionDetailScreenOnlinePreview() {
    DialogTheme {
        Surface {
            DiscussionDetailScreenScaffold(
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
                onCommentEditClick = { _, _ -> },
                onCommentDeleteClick = {},
                onCommentReportClick = {},
                onBookmarkClick = {},
                onLikeClick = {},
                onParticipateClick = {},
                onSummaryClick = {},
                onEditClick = {},
                onDeleteClick = {},
                onReportClick = {},
            )
        }
    }
}
