package com.on.dialog.feature.mycreated.impl.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.DiscussionRepository
import com.on.dialog.feature.mycreated.impl.model.DiscussionUiModel.Companion.toUiModel
import com.on.dialog.model.discussion.cursorpage.DiscussionCatalogCursorPage
import com.on.dialog.ui.viewmodel.BaseViewModel
import dialog.feature.mycreated.impl.generated.resources.Res
import dialog.feature.mycreated.impl.generated.resources.fetch_my_created_discussions_failure_message
import io.github.aakira.napier.Napier
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@Stable
internal class MyCreatedViewModel(
    private val discussionRepository: DiscussionRepository,
) : BaseViewModel<MyCreatedIntent, MyCreatedState, MyCreatedEffect>(MyCreatedState.Loading()) {
    private var nextCursor: String? = null
    private var hasNext: Boolean = true

    init {
        fetchMyCreatedDiscussions()
    }

    override fun onIntent(intent: MyCreatedIntent) {
        when (intent) {
            MyCreatedIntent.LoadNextPage -> fetchMyCreatedDiscussions()
            MyCreatedIntent.Refresh -> refresh()
        }
    }

    private fun fetchMyCreatedDiscussions() {
        if (!hasNext) return

        viewModelScope
            .launch {
                discussionRepository
                    .getMyDiscussions(cursor = nextCursor, size = FETCH_SIZE)
                    .onSuccess(::handleFetchMyCreatedDiscussionsSuccess)
                    .onFailure(::handleFetchMyCreatedDiscussionsFailure)
            }
    }

    private fun handleFetchMyCreatedDiscussionsSuccess(result: DiscussionCatalogCursorPage) {
        nextCursor = result.nextCursor
        hasNext = result.hasNext

        updateState {
            val newDiscussions = result.discussionCatalog.map { catalog -> catalog.toUiModel() }
            update(newDiscussions.toImmutableList())
        }
    }

    private fun handleFetchMyCreatedDiscussionsFailure(throwable: Throwable) {
        Napier.e(throwable.message.orEmpty(), throwable)
        updateState { update(persistentListOf()) }
        emitEffect(
            MyCreatedEffect.ShowSnackbar(
                message = Res.string.fetch_my_created_discussions_failure_message,
                state = SnackbarState.NEGATIVE,
            ),
        )
    }

    private fun refresh() {
        nextCursor = null
        hasNext = true
        fetchMyCreatedDiscussions()
    }

    companion object {
        private const val FETCH_SIZE = 10
    }
}
