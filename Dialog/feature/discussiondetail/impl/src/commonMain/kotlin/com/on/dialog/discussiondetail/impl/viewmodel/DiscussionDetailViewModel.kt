package com.on.dialog.discussiondetail.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.core.common.error.NetworkError
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.discussiondetail.impl.model.DiscussionCommentUiModel.Companion.toUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.Companion.toUiModel
import com.on.dialog.discussiondetail.impl.model.ReportReasonUiModel
import com.on.dialog.domain.repository.CommentRepository
import com.on.dialog.domain.repository.DiscussionRepository
import com.on.dialog.domain.repository.LikeRepository
import com.on.dialog.domain.repository.ParticipantRepository
import com.on.dialog.domain.repository.ReportRepository
import com.on.dialog.domain.repository.ScrapRepository
import com.on.dialog.domain.repository.SessionRepository
import com.on.dialog.domain.usecase.discussion.interaction.ToggleDiscussionBookmarkUseCase
import com.on.dialog.domain.usecase.discussion.interaction.ToggleDiscussionLikeUseCase
import com.on.dialog.domain.usecase.discussion.summary.GenerateDiscussionSummaryUseCase
import com.on.dialog.model.discussion.comment.DiscussionComment
import com.on.dialog.model.discussion.detail.DiscussionDetail
import com.on.dialog.ui.mapper.UNKNOWN_DIALOG_ERROR
import com.on.dialog.ui.mapper.toDialogErrorStringResource
import com.on.dialog.ui.viewmodel.BaseViewModel
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.error_fetch_discussion_detail
import dialog.feature.discussiondetail.impl.generated.resources.message_comment_delete_success
import dialog.feature.discussiondetail.impl.generated.resources.message_comment_edit_success
import dialog.feature.discussiondetail.impl.generated.resources.message_discussion_report_success
import io.github.aakira.napier.Napier
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

internal class DiscussionDetailViewModel(
    private val discussionId: Long,
    private val discussionRepository: DiscussionRepository,
    private val likeRepository: LikeRepository,
    private val scrapRepository: ScrapRepository,
    private val commentRepository: CommentRepository,
    private val participantRepository: ParticipantRepository,
    private val sessionRepository: SessionRepository,
    private val reportRepository: ReportRepository,
    private val generateDiscussionSummaryUseCase: GenerateDiscussionSummaryUseCase,
    private val toggleDiscussionBookmarkUseCase: ToggleDiscussionBookmarkUseCase,
    private val toggleDiscussionLikeUseCase: ToggleDiscussionLikeUseCase,
) : BaseViewModel<DiscussionDetailIntent, DiscussionDetailState, DiscussionDetailEffect>(
        initialState = DiscussionDetailState(),
    ) {
    private var cachedUserId: Long? = null

    init {
        fetchInitialData()
    }

    override fun onIntent(intent: DiscussionDetailIntent) {
        when (intent) {
            DiscussionDetailIntent.ToggleBookmark -> toggleBookmark()

            DiscussionDetailIntent.ToggleLike -> toggleLike()

            DiscussionDetailIntent.Participate -> participate()

            DiscussionDetailIntent.GenerateSummary -> generateSummary()

            is DiscussionDetailIntent.OnReportDiscussion -> reportDiscussion(reason = intent.reason)

            is DiscussionDetailIntent.OnReportComment -> reportComment(
                commentId = intent.commentId,
                reason = intent.reason,
            )

            is DiscussionDetailIntent.OnSubmitComment -> submitComment(content = intent.content)

            is DiscussionDetailIntent.OnSubmitReply -> submitReply(
                commentId = intent.commentId,
                content = intent.content,
            )

            is DiscussionDetailIntent.OnEditComment -> editComment(
                commentId = intent.commentId,
                content = intent.content,
            )

            is DiscussionDetailIntent.OnDeleteComment -> deleteComment(commentId = intent.commentId)

            DiscussionDetailIntent.OnDeleteDiscussion -> deleteDiscussion()
        }
    }

    private fun fetchInitialData() {
        updateState { copy(isLoading = true) }
        viewModelScope
            .launch {
                awaitAll(
                    async { fetchDiscussionDetail() },
                    async { fetchBookmarkStatus() },
                    async { fetchLikeStatus() },
                    async { fetchComments() },
                )
            }.invokeOnCompletion { updateState { copy(isLoading = false) } }
    }

    private suspend fun fetchDiscussionDetail() {
        discussionRepository
            .getDiscussionDetail(id = discussionId)
            .onSuccess { discussionDetail ->
                handleFetchDiscussionDetailSuccess(
                    discussionDetail = discussionDetail,
                    userId = getUserId(),
                )
            }.onFailure { handleFetchDiscussionDetailFailure() }
    }

    private fun handleFetchDiscussionDetailSuccess(
        discussionDetail: DiscussionDetail,
        userId: Long,
    ) {
        updateState {
            copy(
                discussion = discussionDetail.toUiModel(userId = userId),
                likeCount = discussionDetail.detailContent.likeCount,
                isMyDiscussion = discussionDetail.detailContent.author.id == userId,
            )
        }
    }

    private fun handleFetchDiscussionDetailFailure() {
        emitEffect(
            DiscussionDetailEffect.ShowSnackbar(
                message = Res.string.error_fetch_discussion_detail,
                state = SnackbarState.NEGATIVE,
            ),
        )
        emitEffect(DiscussionDetailEffect.NavigateHome)
    }

    private suspend fun fetchBookmarkStatus() {
        scrapRepository
            .getScrapStatus(discussionId = discussionId)
            .onSuccess { updateState { copy(isBookmarked = it.isScraped) } }
    }

    private fun toggleBookmark() {
        val wasBookmarked = currentState.isBookmarked
        updateState { copy(isBookmarked = !wasBookmarked) }

        viewModelScope.launch {
            toggleDiscussionBookmarkUseCase(
                discussionId = discussionId,
                isCurrentlyBookmarked = wasBookmarked,
            ).onFailure { throwable ->
                updateState { copy(isBookmarked = wasBookmarked) }
                showErrorSnackbar(throwable = throwable)
            }
        }
    }

    private suspend fun fetchLikeStatus() {
        likeRepository
            .getLikeStatus(discussionId = discussionId)
            .onSuccess { updateState { copy(isLiked = it.isLiked) } }
    }

    private suspend fun fetchComments(): Result<List<DiscussionComment>> =
        commentRepository
            .fetchComments(discussionId = discussionId)
            .onSuccess { comments ->
                val userId = getUserId()
                val newComments = comments.map { comment -> comment.toUiModel(userId = userId) }
                updateState { copy(comments = newComments.toImmutableList()) }
            }.onFailure(::showErrorSnackbar)

    private fun toggleLike() {
        val wasLiked = currentState.isLiked
        val likeDelta = if (wasLiked) -1 else 1
        updateState {
            copy(isLiked = !wasLiked, likeCount = likeCount + likeDelta)
        }

        viewModelScope.launch {
            toggleDiscussionLikeUseCase(
                discussionId = discussionId,
                isCurrentlyLiked = wasLiked,
            ).onFailure { throwable ->
                updateState { copy(isLiked = wasLiked, likeCount = likeCount - likeDelta) }
                showErrorSnackbar(throwable = throwable)
            }
        }
    }

    private suspend fun getUserId(): Long {
        cachedUserId?.let { userId -> return userId }

        return sessionRepository
            .getUserId()
            .onSuccess { userId -> cachedUserId = userId }
            .getOrNull() ?: UNKNOWN_USER_ID
    }

    private fun participate() {
        viewModelScope.launch {
            val participationResult =
                participantRepository.postParticipation(discussionId = discussionId)
            participationResult.onFailure(::showErrorSnackbar)

            if (participationResult.isSuccess) {
                fetchDiscussionDetail()
            }
        }
    }

    private fun generateSummary() {
        if (currentState.isGeneratingSummary) return
        updateState { copy(isGeneratingSummary = true) }

        viewModelScope
            .launch {
                generateDiscussionSummaryUseCase(discussionId = discussionId)
                    .onSuccess(::handleGenerateSummarySuccess)
                    .onFailure(::showErrorSnackbar)
            }.invokeOnCompletion { updateState { copy(isGeneratingSummary = false) } }
    }

    private fun handleGenerateSummarySuccess(summary: String) {
        val discussion = currentState.discussion
            as? DiscussionDetailUiModel.OnlineDiscussionDetailUiModel ?: return
        updateState { copy(discussion = discussion.copy(summary = summary)) }
    }

    private fun submitComment(content: String) {
        viewModelScope.launch {
            commentRepository
                .postComment(discussionId = discussionId, content = content)
                .onSuccess { fetchComments() }
                .onFailure(::showErrorSnackbar)
        }
    }

    private fun submitReply(commentId: Long, content: String) {
        viewModelScope.launch {
            commentRepository
                .postReply(
                    discussionId = discussionId,
                    parentCommentId = commentId,
                    content = content,
                ).onSuccess { fetchComments() }
                .onFailure(::showErrorSnackbar)
        }
    }

    private fun editComment(commentId: Long, content: String) {
        viewModelScope.launch {
            commentRepository
                .patchComment(discussionCommentId = commentId, content = content)
                .onSuccess {
                    fetchComments()
                    showSnackbar(
                        message = Res.string.message_comment_edit_success,
                        state = SnackbarState.POSITIVE,
                    )
                }.onFailure(::showErrorSnackbar)
        }
    }

    private fun deleteComment(commentId: Long) {
        viewModelScope.launch {
            commentRepository
                .deleteComment(discussionCommentId = commentId)
                .onSuccess {
                    fetchComments()
                    showSnackbar(
                        message = Res.string.message_comment_delete_success,
                        state = SnackbarState.POSITIVE,
                    )
                }.onFailure(::showErrorSnackbar)
        }
    }

    private fun deleteDiscussion() {
        viewModelScope.launch {
            discussionRepository
                .deleteDiscussion(id = discussionId)
                .onSuccess {
                    showSnackbar(
                        message = Res.string.message_comment_delete_success,
                        state = SnackbarState.POSITIVE,
                    )
                    emitEffect(DiscussionDetailEffect.NavigateHome)
                }.onFailure(::showErrorSnackbar)
        }
    }

    private fun reportDiscussion(reason: ReportReasonUiModel) {
        viewModelScope.launch {
            reportRepository
                .reportDiscussion(
                    discussionId = discussionId,
                    reason = reason.name,
                ).onSuccess {
                    showSnackbar(
                        message = Res.string.message_discussion_report_success,
                        state = SnackbarState.POSITIVE,
                    )
                }.onFailure(::showErrorSnackbar)
        }
    }

    private fun reportComment(commentId: Long, reason: ReportReasonUiModel) {
        viewModelScope.launch {
            reportRepository
                .reportComment(
                    discussionId = discussionId,
                    commentId = commentId,
                    reason = reason.name,
                ).onSuccess {
                    showSnackbar(
                        message = Res.string.message_discussion_report_success,
                        state = SnackbarState.POSITIVE,
                    )
                }.onFailure(::showErrorSnackbar)
        }
    }

    private fun showSnackbar(
        message: StringResource,
        state: SnackbarState,
    ) {
        emitEffect(DiscussionDetailEffect.ShowSnackbar(message = message, state = state))
    }

    private fun showErrorSnackbar(throwable: Throwable) {
        Napier.e(message = throwable.message.orEmpty(), throwable = throwable)

        val message = when (throwable) {
            is NetworkError -> throwable.errorCode.toDialogErrorStringResource()
            else -> UNKNOWN_DIALOG_ERROR
        }
        showSnackbar(message = message, state = SnackbarState.NEGATIVE)
    }

    companion object {
        private const val UNKNOWN_USER_ID = -1L
    }
}
