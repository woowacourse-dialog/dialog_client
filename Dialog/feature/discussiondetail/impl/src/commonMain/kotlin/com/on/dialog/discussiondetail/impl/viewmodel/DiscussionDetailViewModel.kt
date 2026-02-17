package com.on.dialog.discussiondetail.impl.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.Companion.toUiModel
import com.on.dialog.domain.repository.DiscussionRepository
import com.on.dialog.domain.repository.LikeRepository
import com.on.dialog.domain.repository.ParticipantRepository
import com.on.dialog.domain.repository.ScrapRepository
import com.on.dialog.ui.viewmodel.BaseViewModel
import com.on.dialog.ui.viewmodel.UiEffect
import com.on.dialog.ui.viewmodel.UiIntent
import com.on.dialog.ui.viewmodel.UiState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

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
) : UiState

sealed interface DiscussionDetailEffect : UiEffect

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
            launch {
                discussionRepository
                    .getDiscussionDetail(id = discussionId)
                    .onSuccess {
                        updateState {
                            copy(
                                discussion = it.toUiModel(),
                                likeCount = it.detailContent.likeCount,
                                isLoading = false,
                            )
                        }
                    }.onFailure { Napier.d("토론 상세 화면을 불러오는 데 실패했습니다.") }
            }
            launch {
                scrapRepository
                    .getScrapStatus(discussionId = discussionId)
                    .onSuccess { updateState { copy(isBookmarked = it.isScraped) } }
            }
            launch {
                likeRepository
                    .getLikeStatus(discussionId = discussionId)
                    .onSuccess { updateState { copy(isLiked = it.isLiked) } }
            }
        }
    }

    private fun updateBookmark() {
        val isCurrentlyBookmarked = uiState.value.isBookmarked
        updateState { copy(isBookmarked = !isCurrentlyBookmarked) }

        viewModelScope.launch {
            if (isCurrentlyBookmarked) {
                scrapRepository.deleteScrap(discussionId)
            } else {
                scrapRepository.postScrap(discussionId)
            }.onFailure {
                updateState { copy(isBookmarked = isCurrentlyBookmarked) }
                Napier.d("북마크 업데이트에 실패했습니다.")
            }
        }
    }

    private fun updateLike() {
        val isCurrentlyLiked = uiState.value.isLiked
        updateState { copy(isLiked = !isCurrentlyLiked) }

        viewModelScope.launch {
            if (isCurrentlyLiked) {
                likeRepository.deleteLike(discussionId)
            } else {
                likeRepository.postLike(discussionId)
            }.onSuccess { updateState { copy(likeCount = likeCount + if (isCurrentlyLiked) -1 else 1) } }
                .onFailure {
                    updateState { copy(isLiked = isCurrentlyLiked) }
                    Napier.d("좋아요 업데이트에 실패했습니다.")
                }
        }
    }

    private fun participate() {}

    private fun generateSummary() {}
}
