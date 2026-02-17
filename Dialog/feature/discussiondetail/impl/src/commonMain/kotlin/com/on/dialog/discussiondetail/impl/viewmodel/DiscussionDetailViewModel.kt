package com.on.dialog.discussiondetail.impl.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.on.dialog.core.common.error.NetworkError
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.Companion.toUiModel
import com.on.dialog.domain.repository.DiscussionRepository
import com.on.dialog.domain.repository.LikeRepository
import com.on.dialog.domain.repository.ParticipantRepository
import com.on.dialog.domain.repository.ScrapRepository
import com.on.dialog.model.discussion.content.DiscussionType
import com.on.dialog.ui.viewmodel.BaseViewModel
import com.on.dialog.ui.viewmodel.UiEffect
import com.on.dialog.ui.viewmodel.UiIntent
import com.on.dialog.ui.viewmodel.UiState
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.error_common
import dialog.feature.discussiondetail.impl.generated.resources.error_should_login
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

sealed interface DiscussionDetailIntent : UiIntent {
    data object ToggleBookmark : DiscussionDetailIntent

    data object ToggleLike : DiscussionDetailIntent

    data object Participate : DiscussionDetailIntent

    data object GenerateSummary : DiscussionDetailIntent
}

@Immutable
data class DiscussionDetailState(
    val isLoading: Boolean = true,
    val discussion: DiscussionDetailUiModel? = null,
    val isBookmarked: Boolean = false,
    val isLiked: Boolean = false,
    val likeCount: Int = 0,
    val isParticipating: Boolean = false,
) : UiState

sealed interface DiscussionDetailEffect : UiEffect {
    data class ShowSnackbar(
        val message: StringResource,
        val state: SnackbarState,
    ) : DiscussionDetailEffect
}

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
        viewModelScope.launch {
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
            .onSuccess {
                updateState {
                    copy(discussion = it.toUiModel(), likeCount = it.detailContent.likeCount)
                }
            }.onFailure { Napier.d("토론 상세 화면을 불러오는 데 실패했습니다.") }
    }

    private suspend fun fetchBookmarkStatus() {
        scrapRepository
            .getScrapStatus(discussionId = discussionId)
            .onSuccess { updateState { copy(isBookmarked = it.isScraped) } }
    }

    private fun updateBookmark() {
        val isCurrentlyBookmarked = uiState.value.isBookmarked
        updateState { copy(isBookmarked = !isCurrentlyBookmarked) }

        viewModelScope.launch {
            if (isCurrentlyBookmarked) {
                scrapRepository.deleteScrap(discussionId = discussionId)
            } else {
                scrapRepository.postScrap(discussionId = discussionId)
            }.onFailure {
                updateState { copy(isBookmarked = isCurrentlyBookmarked) }
                showErrorSnackbar(it)
            }
        }
    }

    private suspend fun fetchLikeStatus() {
        likeRepository
            .getLikeStatus(discussionId = discussionId)
            .onSuccess { updateState { copy(isLiked = it.isLiked) } }
    }

    private fun updateLike() {
        val isCurrentlyLiked = uiState.value.isLiked
        updateState { copy(isLiked = !isCurrentlyLiked) }

        viewModelScope.launch {
            if (isCurrentlyLiked) {
                likeRepository.deleteLike(discussionId = discussionId)
            } else {
                likeRepository.postLike(discussionId = discussionId)
            }.onSuccess { updateState { copy(likeCount = likeCount + if (isCurrentlyLiked) -1 else 1) } }
                .onFailure {
                    updateState { copy(isLiked = isCurrentlyLiked) }
                    showErrorSnackbar(it)
                }
        }
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

    private fun generateSummary() {}

    private fun showErrorSnackbar(throwable: Throwable) {
        val message = when (throwable) {
            is NetworkError.Unauthorized -> Res.string.error_should_login
            else -> Res.string.error_common
        }
        emitEffect(
            DiscussionDetailEffect.ShowSnackbar(message = message, state = SnackbarState.NEGATIVE),
        )
    }
}
