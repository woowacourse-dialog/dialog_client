package com.on.dialog.feature.discussionlist.impl.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.on.dialog.domain.repository.DiscussionRepository
import com.on.dialog.feature.discussionlist.impl.model.DiscussionStatusUiModel
import com.on.dialog.feature.discussionlist.impl.model.DiscussionTypeUiModel
import com.on.dialog.feature.discussionlist.impl.model.DiscussionUiModel.Companion.toUiModel
import com.on.dialog.feature.discussionlist.impl.model.TrackUiModel
import com.on.dialog.model.discussion.cursorpage.DiscussionCatalogCursorPage
import com.on.dialog.ui.viewmodel.BaseViewModel
import io.github.aakira.napier.Napier
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@Stable
internal class DiscussionListViewModel(
    private val discussionRepository: DiscussionRepository,
) : BaseViewModel<DiscussionListIntent, DiscussionListState, DiscussionListEffect>(
        DiscussionListState(),
    ) {
    private var nextCursor: String? = null
    private var hasNext: Boolean = true

    private val filterChanged = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1,
    )

    private var fetchJob: Job? = null

    init {
        filterChanged
            .debounce(FILTER_DEBOUNCE_MILLIS)
            .onEach { refreshListInternal() }
            .launchIn(viewModelScope)

        fetchDiscussions()
    }

    override fun onIntent(intent: DiscussionListIntent) {
        when (intent) {
            is DiscussionListIntent.ClickTrackFilter -> toggleTrackFilter(intent.track)

            is DiscussionListIntent.ClickDiscussionTypeFilter -> toggleDiscussionTypeFilter(intent.discussionType)

            is DiscussionListIntent.ClickDiscussionStatusFilter -> toggleDiscussionStatusFilter(
                intent.discussionStatus,
            )

            is DiscussionListIntent.RefreshList -> refreshListImmediate()

            is DiscussionListIntent.LoadNextPage -> fetchDiscussions()
        }
    }

    private fun fetchDiscussions() {
        if (!hasNext) return

        fetchJob?.cancel()
        updateState { copy(isLoading = true) }

        fetchJob = viewModelScope
            .launch {
                discussionRepository
                    .getDiscussions(
                        discussionCriteria = currentState.filter.toDomain(),
                        cursor = nextCursor,
                        size = 10,
                    ).onSuccess(::handleFetchDiscussionsSuccess)
                    .onFailure(::handleFetchDiscussionsFailure)
            }.apply {
                invokeOnCompletion { updateState { copy(isLoading = false, isRefreshing = false) } }
            }
    }

    private fun handleFetchDiscussionsSuccess(result: DiscussionCatalogCursorPage) {
        nextCursor = result.nextCursor
        hasNext = result.hasNext

        updateState {
            val newDiscussions = result.discussionCatalog.map { it.toUiModel() }
            val mergedDiscussions =
                (discussions + newDiscussions)
                    .distinctBy { discussion -> discussion.id }
                    .toImmutableList()
            copy(discussions = mergedDiscussions)
        }
    }

    private fun handleFetchDiscussionsFailure(throwable: Throwable) {
        Napier.e(throwable.message.orEmpty(), throwable)
        emitEffect(DiscussionListEffect.ShowSnackbar(message = "토론 목록을 불러오는데 실패했어요"))
    }

    private fun refreshListImmediate() {
        updateState { copy(isRefreshing = true) }
        refreshListInternal()
    }

    private fun refreshListInternal() {
        hasNext = true
        nextCursor = null
        fetchDiscussions()
    }

    private fun toggleTrackFilter(track: TrackUiModel) {
        val newFilters = currentState.filter.updateTrackFilter(track)
        updateState { copy(filter = newFilters) }
        filterChanged.tryEmit(Unit)
    }

    private fun toggleDiscussionTypeFilter(discussionType: DiscussionTypeUiModel) {
        val newFilters = currentState.filter.updateDiscussionTypeFilter(discussionType)
        updateState { copy(filter = newFilters) }
        filterChanged.tryEmit(Unit)
    }

    private fun toggleDiscussionStatusFilter(discussionStatus: DiscussionStatusUiModel) {
        val newFilters = currentState.filter.updateDiscussionStatusFilter(discussionStatus)
        updateState { copy(filter = newFilters) }
        filterChanged.tryEmit(Unit)
    }

    private companion object {
        const val FILTER_DEBOUNCE_MILLIS = 500L
    }
}
