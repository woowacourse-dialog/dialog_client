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
import io.github.aakira.napier.Napier
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@Stable
internal class ScrapViewModel(
    private val scrapRepository: ScrapRepository,
) : BaseViewModel<ScrapIntent, ScrapState, ScrapEffect>(ScrapState.Loading()) {
    private var nextCursorId: Long? = null
    private var hasNext: Boolean = true
    private var previousScrapCatalogs: ImmutableList<ScrapCatalog> = persistentListOf()

    init {
        observeScrapCatalogs()
        fetchScraps()
    }

    override fun onIntent(intent: ScrapIntent) {
        when (intent) {
            ScrapIntent.LoadNextPage -> fetchScraps()
            ScrapIntent.Refresh -> refresh()
            is ScrapIntent.LoginStatusChanged -> handleLoginStatusChanged(intent.isLoggedIn)
        }
    }

    private fun fetchScraps() {
        if (!hasNext) return

        viewModelScope
            .launch {
                scrapRepository
                    .getScraps(lastCursorId = nextCursorId, size = FETCH_SIZE)
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
        nextCursorId = result.nextCursorId
        hasNext = result.hasNext
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
        nextCursorId = null
        hasNext = true
        updateState { ScrapState.UnAuthorized }
    }

    private fun refresh() {
        nextCursorId = null
        hasNext = true
        fetchScraps()
    }

    private fun observeScrapCatalogs() {
        viewModelScope.launch {
            scrapRepository.scrapCatalogs.collect { currentCatalogs: ImmutableList<ScrapCatalog> ->
                previousScrapCatalogs = currentCatalogs

                updateState {
                    if (previousScrapCatalogs.isEmpty()) {
                        ScrapState.Empty
                    } else {
                        ScrapState.Content(
                            previousScrapCatalogs
                                .map { it.toUiModel() }
                                .toImmutableList(),
                        )
                    }
                }
            }
        }
    }

    private fun handleLoginStatusChanged(isLoggedIn: Boolean?) {
        when (isLoggedIn) {
            null -> {
                Unit
            }

            true -> {
                refresh()
            }

            false -> {
                nextCursorId = null
                hasNext = true
                updateState { ScrapState.UnAuthorized }
            }
        }
    }

    companion object {
        private const val FETCH_SIZE = 10
    }
}
