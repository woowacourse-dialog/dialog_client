package com.on.dialog.discussiondetail.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.core.common.error.NetworkError
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.Companion.toUiModel
import com.on.dialog.domain.repository.DiscussionRepository
import com.on.dialog.domain.repository.LikeRepository
import com.on.dialog.domain.repository.ParticipantRepository
import com.on.dialog.domain.repository.ScrapRepository
import com.on.dialog.model.discussion.content.DiscussionType
import com.on.dialog.model.discussion.detail.DiscussionDetail
import com.on.dialog.ui.viewmodel.BaseViewModel
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.error_already_started
import dialog.feature.discussiondetail.impl.generated.resources.error_common
import dialog.feature.discussiondetail.impl.generated.resources.error_fetch_discussion_detail
import dialog.feature.discussiondetail.impl.generated.resources.error_not_my_discussion
import dialog.feature.discussiondetail.impl.generated.resources.error_should_login
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

class DiscussionDetailViewModel(
    private val discussionId: Long,
    private val discussionRepository: DiscussionRepository,
    private val likeRepository: LikeRepository,
    private val scrapRepository: ScrapRepository,
    private val participantRepository: ParticipantRepository,
) : BaseViewModel<DiscussionDetailIntent, DiscussionDetailState, DiscussionDetailEffect>(
    initialState = DiscussionDetailState(),
) {
    init {
        fetchDiscussion()
    }

    override fun onIntent(intent: DiscussionDetailIntent) {
        when (intent) {
            DiscussionDetailIntent.ToggleBookmark -> updateBookmark()
            DiscussionDetailIntent.ToggleLike -> updateLike()
            DiscussionDetailIntent.Participate -> participate()
            DiscussionDetailIntent.GenerateSummary -> generateSummary()
        }
    }

    private fun fetchDiscussion() {
        viewModelScope
            .launch {
                launch { fetchBookmarkStatus() }
                launch { fetchLikeStatus() }
                launch {
                    fetchDiscussionDetail()
                    if (currentState.discussion?.discussionType == DiscussionType.OFFLINE) {
                        fetchParticipationStatus()
                    }
                }
            }.invokeOnCompletion { updateState { copy(isLoading = false) } }
    }

    private suspend fun fetchDiscussionDetail() {
        discussionRepository
            .getDiscussionDetail(id = discussionId)
            .onSuccess(::handleFetchDiscussionDetailSuccess)
            .onFailure { handleFetchDiscussionDetailFailure() }
    }

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
            )
        )
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

    private fun updateLike() {
        val isCurrentlyLiked = currentState.isLiked
        updateState { copy(isLiked = !isCurrentlyLiked) }

        viewModelScope.launch {
            if (isCurrentlyLiked) {
                likeRepository.deleteLike(discussionId = discussionId)
            } else {
                likeRepository.postLike(discussionId = discussionId)
            }
                .onSuccess { handleUpdateLikeSuccess(isCurrentlyLiked) }
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

    private suspend fun fetchParticipationStatus() {
        participantRepository
            .getParticipationStatus(discussionId = discussionId)
            .onSuccess { updateState { copy(isParticipating = it.isParticipation) } }
    }

    private fun participate() {
        viewModelScope.launch {
            participantRepository
                .postParticipation(discussionId = discussionId)
                .onSuccess { handleParticipateSuccess() }
                .onFailure { showErrorSnackbar(it) }
        }
    }

    private fun handleParticipateSuccess() {
        viewModelScope.launch {
            launch { fetchDiscussionDetail() }
            launch { fetchParticipationStatus() }
        }
    }

    private fun generateSummary() {
        viewModelScope.launch {
            discussionRepository
                .createDiscussionSummary(discussionId = discussionId)
                .onSuccess { fetchDiscussionDetail() }
                .onFailure { showErrorSnackbar(it) }
        }
    }

    private fun showErrorSnackbar(throwable: Throwable) {
        val message = when (throwable) {
            is NetworkError.Unauthorized -> Res.string.error_should_login
            is NetworkError.ServerCustomError -> errorCodeToStringRes(throwable.errorCode)
            else -> Res.string.error_common
        }
        emitEffect(
            DiscussionDetailEffect.ShowSnackbar(message = message, state = SnackbarState.NEGATIVE),
        )
    }

    private fun errorCodeToStringRes(errorCode: String): StringResource = when (errorCode) {
        ERROR_CODE_ALREADY_STARTED -> Res.string.error_already_started
        ERROR_CODE_NOT_MY_DISCUSSION -> Res.string.error_not_my_discussion
        else -> Res.string.error_common
    }

    companion object {
        private const val ERROR_CODE_ALREADY_STARTED = "5026"
        private const val ERROR_CODE_NOT_MY_DISCUSSION = "5031"
    }
}
