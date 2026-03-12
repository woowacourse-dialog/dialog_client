package com.on.dialog.discussiondetail.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.core.common.error.NetworkError
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.discussiondetail.impl.model.CommentType
import com.on.dialog.discussiondetail.impl.model.DiscussionCommentUiModel.Companion.toUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.Companion.toUiModel
import com.on.dialog.domain.repository.CommentRepository
import com.on.dialog.domain.repository.DiscussionRepository
import com.on.dialog.domain.repository.LikeRepository
import com.on.dialog.domain.repository.ParticipantRepository
import com.on.dialog.domain.repository.ScrapRepository
import com.on.dialog.domain.repository.SessionRepository
import com.on.dialog.model.discussion.comment.DiscussionComment
import com.on.dialog.model.discussion.detail.DiscussionDetail
import com.on.dialog.model.discussion.detail.OfflineDiscussionDetail
import com.on.dialog.ui.mapper.UNKNOWN_DIALOG_ERROR
import com.on.dialog.ui.mapper.toDialogErrorStringResource
import com.on.dialog.ui.viewmodel.BaseViewModel
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.error_fetch_discussion_detail
import dialog.feature.discussiondetail.impl.generated.resources.message_comment_delete_success
import dialog.feature.discussiondetail.impl.generated.resources.message_comment_edit_success
import io.github.aakira.napier.Napier
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
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
) : BaseViewModel<DiscussionDetailIntent, DiscussionDetailState, DiscussionDetailEffect>(
        initialState = DiscussionDetailState(),
    ) {
    private var cachedUserId: Long? = null

    init {
        fetchDiscussion()
    }

    override fun onIntent(intent: DiscussionDetailIntent) {
        when (intent) {
            DiscussionDetailIntent.ToggleBookmark -> updateBookmark()

            DiscussionDetailIntent.ToggleLike -> updateLike()

            DiscussionDetailIntent.Participate -> participate()

            DiscussionDetailIntent.GenerateSummary -> generateSummary()

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

            is DiscussionDetailIntent.OpenCommentEditor -> openCommentEditor(type = intent.type)

            DiscussionDetailIntent.CloseCommentEditor -> closeCommentEditor()

            is DiscussionDetailIntent.OpenDeleteCommentDialog -> openDeleteCommentDialog(commentId = intent.commentId)

            DiscussionDetailIntent.CloseDeleteCommentDialog -> closeDeleteCommentDialog()
        }
    }

    private fun openCommentEditor(type: CommentType) {
        updateState { copy(commentType = type, deleteCommentId = null) }
    }

    private fun closeCommentEditor() {
        updateState { copy(commentType = null) }
    }

    private fun openDeleteCommentDialog(commentId: Long) {
        updateState { copy(deleteCommentId = commentId, commentType = null) }
    }

    private fun closeDeleteCommentDialog() {
        updateState { copy(deleteCommentId = null) }
    }

    private fun fetchDiscussion() {
        viewModelScope
            .launch {
                awaitAll(
                    async { fetchBookmarkStatus() },
                    async { fetchLikeStatus() },
                    async { fetchDiscussionDetail() },
                    async { fetchComments() },
                )
            }.invokeOnCompletion { updateState { copy(isLoading = false) } }
    }

    private suspend fun fetchDiscussionDetail(): Result<DiscussionDetail> =
        discussionRepository
            .getDiscussionDetail(id = discussionId)
            .onSuccess { discussionDetail ->
                handleFetchDiscussionDetailSuccess(discussionDetail)
                updateIsMyDiscussion(authorId = discussionDetail.detailContent.author.id)
                if (discussionDetail is OfflineDiscussionDetail) {
                    fetchParticipationStatus()
                }
            }.onFailure { handleFetchDiscussionDetailFailure() }

    private fun handleFetchDiscussionDetailSuccess(discussionDetail: DiscussionDetail) =
        with(discussionDetail) {
            updateState {
                copy(discussion = toUiModel(), likeCount = detailContent.likeCount)
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

    private fun updateBookmark() {
        val isCurrentlyBookmarked = currentState.isBookmarked
        updateState { copy(isBookmarked = !isCurrentlyBookmarked) }

        viewModelScope.launch {
            if (isCurrentlyBookmarked) {
                scrapRepository.deleteScrap(discussionId = discussionId)
            } else {
                scrapRepository.postScrap(discussionId = discussionId)
            }.onFailure { handleUpdateBookmarkFailure(isCurrentlyBookmarked, it) }
        }
    }

    private fun handleUpdateBookmarkFailure(isCurrentlyBookmarked: Boolean, throwable: Throwable) {
        updateState { copy(isBookmarked = isCurrentlyBookmarked) }
        showErrorSnackbar(throwable = throwable)
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
                val newComments =
                    comments.map { comment -> comment.toUiModel(userId = userId) }.toImmutableList()
                updateState { copy(comments = newComments) }
            }.onFailure { throwable ->
                Napier.e(message = throwable.message.orEmpty(), throwable = throwable)
            }

    private fun updateLike() {
        val isCurrentlyLiked = currentState.isLiked
        updateState { copy(isLiked = !isCurrentlyLiked) }

        viewModelScope.launch {
            if (isCurrentlyLiked) {
                likeRepository.deleteLike(discussionId = discussionId)
            } else {
                likeRepository.postLike(discussionId = discussionId)
            }.onSuccess { handleUpdateLikeSuccess(isCurrentlyLiked) }
                .onFailure { handleUpdateLikeFailure(isCurrentlyLiked, it) }
        }
    }

    private fun handleUpdateLikeSuccess(isCurrentlyLiked: Boolean) {
        updateState { copy(likeCount = likeCount + if (isCurrentlyLiked) -1 else 1) }
    }

    private fun handleUpdateLikeFailure(isCurrentlyLiked: Boolean, throwable: Throwable) {
        updateState { copy(isLiked = isCurrentlyLiked) }
        showErrorSnackbar(throwable = throwable)
    }

    private suspend fun updateIsMyDiscussion(authorId: Long) {
        val isMyDiscussion = getUserId() == authorId
        updateState { copy(isMyDiscussion = isMyDiscussion) }
    }

    private suspend fun getUserId(): Long {
        cachedUserId?.let { userId -> return userId }

        return sessionRepository
            .getUserId()
            .onSuccess { userId -> cachedUserId = userId }
            .getOrNull() ?: UNKNOWN_USER_ID
    }

    private suspend fun fetchParticipationStatus() {
        participantRepository
            .getParticipationStatus(discussionId = discussionId)
            .onSuccess { updateState { copy(isParticipating = it.isParticipation) } }
    }

    private fun participate() {
        viewModelScope.launch {
            participantRepository
                .postParticipation(discussionId = discussionId)
                .onSuccess { refreshAfterParticipate() }
                .onFailure(::showErrorSnackbar)
        }
    }

    private fun refreshAfterParticipate() {
        viewModelScope.launch {
            awaitAll(
                async { fetchDiscussionDetail() },
                async { fetchParticipationStatus() },
            )
        }
    }

    private fun generateSummary() {
        if (currentState.isGeneratingSummary) return
        updateState { copy(isGeneratingSummary = true) }

        viewModelScope
            .launch {
                discussionRepository
                    .createDiscussionSummary(discussionId = discussionId)
                    .onSuccess { pollSummaryUntilLoaded() }
                    .onFailure(::showErrorSnackbar)
            }.invokeOnCompletion { updateState { copy(isGeneratingSummary = false) } }
    }

    private suspend fun pollSummaryUntilLoaded() {
        repeat(SUMMARY_POLL_MAX_RETRY) {
            delay(SUMMARY_POLL_INTERVAL_MS)
            fetchDiscussionDetail()
                .onSuccess { detail -> if (detail.summary != null) return }
        }
    }

    private fun submitComment(content: String) {
        closeCommentEditor()
        viewModelScope.launch {
            commentRepository
                .postComment(discussionId = discussionId, content = content)
                .onSuccess { fetchComments() }
                .onFailure(::showErrorSnackbar)
        }
    }

    private fun submitReply(commentId: Long, content: String) {
        viewModelScope
            .launch {
                commentRepository
                    .postReply(
                        discussionId = discussionId,
                        parentCommentId = commentId,
                        content = content,
                    ).onSuccess { fetchComments() }
                    .onFailure(::showErrorSnackbar)
            }.invokeOnCompletion { closeCommentEditor() }
    }

    private fun editComment(commentId: Long, content: String) {
        viewModelScope
            .launch {
                commentRepository
                    .patchComment(discussionCommentId = commentId, content = content)
                    .onSuccess {
                        fetchComments()
                        showSnackbar(
                            message = Res.string.message_comment_edit_success,
                            state = SnackbarState.POSITIVE,
                        )
                    }.onFailure(::showErrorSnackbar)
            }.invokeOnCompletion { closeCommentEditor() }
    }

    private fun deleteComment(commentId: Long) {
        viewModelScope
            .launch {
                commentRepository
                    .deleteComment(discussionCommentId = commentId)
                    .onSuccess {
                        fetchComments()
                        showSnackbar(
                            message = Res.string.message_comment_delete_success,
                            state = SnackbarState.POSITIVE,
                        )
                    }.onFailure(::showErrorSnackbar)
            }.invokeOnCompletion { closeDeleteCommentDialog() }
    }

    private fun showSnackbar(
        message: StringResource,
        state: SnackbarState,
    ) {
        emitEffect(DiscussionDetailEffect.ShowSnackbar(message = message, state = state))
    }

    private fun showErrorSnackbar(throwable: Throwable) {
        val message = when (throwable) {
            is NetworkError -> throwable.errorCode.toDialogErrorStringResource()
            else -> UNKNOWN_DIALOG_ERROR
        }
        showSnackbar(message = message, state = SnackbarState.NEGATIVE)
    }

    companion object {
        private const val UNKNOWN_USER_ID = -1L
        private const val SUMMARY_POLL_INTERVAL_MS = 10_000L
        private const val SUMMARY_POLL_MAX_RETRY = 3
    }
}
