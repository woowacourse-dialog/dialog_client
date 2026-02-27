package com.on.dialog.scrap.impl.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.on.dialog.core.common.error.NetworkError
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.ScrapRepository
import com.on.dialog.model.discussion.cursorpage.ScrapCatalogCursorPage
import com.on.dialog.model.discussion.scrap.ScrapCatalog
import com.on.dialog.scrap.impl.model.ScrapUiModel.Companion.toUiModel
import com.on.dialog.ui.viewmodel.BaseViewModel
import dialog.feature.scrap.impl.generated.resources.Res
import dialog.feature.scrap.impl.generated.resources.fetch_scrap_discussions_failure_message
import dialog.feature.scrap.impl.generated.resources.fetch_scrap_discussions_failure_unauthorized_message
import io.github.aakira.napier.Napier
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@Stable
internal class ScrapViewModel(
    private val scrapRepository: ScrapRepository,
) : BaseViewModel<ScrapIntent, ScrapState, ScrapEffect>(ScrapState.Loading()) {
    private var nextCursor: Long? = null
    private var hasNext: Boolean = true

    init {
        fetchScraps()
    }

    override fun onIntent(intent: ScrapIntent) {
        when (intent) {
            ScrapIntent.LoadNextPage -> fetchScraps()
            ScrapIntent.Refresh -> refresh()
        }
    }

    private fun fetchScraps() {
        if (!hasNext) return

        viewModelScope
            .launch {
                scrapRepository
                    .getScraps(lastCursorId = nextCursor, size = FETCH_SIZE)
                    .onSuccess(::handleFetchScrapSuccess)
                    .onFailure { it: Throwable ->
                        if (it is NetworkError.Unauthorized) {
                            handleUnauthorized()
                        } else {
                            handleFetchScrapFailure(it)
                        }
                    }
            }
    }

    private fun handleFetchScrapSuccess(result: ScrapCatalogCursorPage) {
        nextCursor = result.nextCursorId
        hasNext = result.hasNext

        updateState {
            val newDiscussions =
                result.scrapCatalog.map { catalog: ScrapCatalog -> catalog.toUiModel() }
            update(newDiscussions.toImmutableList())
        }
    }

    private fun handleFetchScrapFailure(throwable: Throwable) {
        Napier.e(throwable.message.orEmpty(), throwable)
        updateState { update(persistentListOf()) }
        emitEffect(
            ScrapEffect.ShowSnackbar(
                message = Res.string.fetch_scrap_discussions_failure_message,
                state = SnackbarState.NEGATIVE,
            ),
        )
    }

    private fun handleUnauthorized() {
        updateState { ScrapState.Empty }
        emitEffect(
            ScrapEffect.ShowSnackbar(
                message = Res.string.fetch_scrap_discussions_failure_unauthorized_message,
                state = SnackbarState.NEGATIVE,
            ),
        )
    }

    private fun refresh() {
        nextCursor = null
        hasNext = true
        fetchScraps()
    }

    companion object {
        private const val FETCH_SIZE = 10
    }
}
