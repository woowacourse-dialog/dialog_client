package com.on.dialog.discussiondetail.impl.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel.Companion.toUiModel
import com.on.dialog.domain.repository.DiscussionRepository
import com.on.dialog.ui.viewmodel.BaseViewModel
import com.on.dialog.ui.viewmodel.UiEffect
import com.on.dialog.ui.viewmodel.UiIntent
import com.on.dialog.ui.viewmodel.UiState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

sealed interface DiscussionDetailIntent : UiIntent

@Immutable
data class DiscussionDetailState(
    val discussion: DiscussionDetailUiModel? = null,
    val isLoading: Boolean = true,
) : UiState

sealed interface DiscussionDetailEffect : UiEffect

class DiscussionDetailViewModel(
    private val discussionRepository: DiscussionRepository,
) : BaseViewModel<DiscussionDetailIntent, DiscussionDetailState, DiscussionDetailEffect>(
        initialState = DiscussionDetailState(),
    ) {
    override fun onIntent(intent: DiscussionDetailIntent) {
        TODO("Not yet implemented")
    }

    fun fetchDiscussion(id: Long) {
        viewModelScope
            .launch {
                discussionRepository
                    .getDiscussionDetail(id)
                    .onSuccess {
                        updateState { copy(discussion = it.toUiModel(), isLoading = false) }
                    }.onFailure { Napier.d("토론 상세 화면을 불러오는 데 실패했습니다.") }
            }.invokeOnCompletion { updateState { copy(isLoading = true) } }
    }
}
