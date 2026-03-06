package com.on.dialog.feature.discussionlist.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.discussionlist.impl.component.DiscussionListEmptyView
import com.on.dialog.feature.discussionlist.impl.component.DiscussionListFilterSection
import com.on.dialog.feature.discussionlist.impl.component.DiscussionListSection
import com.on.dialog.feature.discussionlist.impl.component.DiscussionListTopAppBar
import com.on.dialog.feature.discussionlist.impl.model.DiscussionStatusUiModel
import com.on.dialog.feature.discussionlist.impl.model.DiscussionTypeUiModel
import com.on.dialog.feature.discussionlist.impl.model.DiscussionUiModel
import com.on.dialog.feature.discussionlist.impl.model.TrackUiModel
import com.on.dialog.feature.discussionlist.impl.viewmodel.DiscussionListEffect
import com.on.dialog.feature.discussionlist.impl.viewmodel.DiscussionListIntent
import com.on.dialog.feature.discussionlist.impl.viewmodel.DiscussionListState
import com.on.dialog.feature.discussionlist.impl.viewmodel.DiscussionListViewModel
import com.on.dialog.ui.extensions.shouldLoadNextPage
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun DiscussionListScreen(
    navigateToDiscussionDetail: (discussionId: Long) -> Unit,
    navigateToCreateDiscussion: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DiscussionListViewModel = koinViewModel(),
) {
    val snackbarState = LocalSnackbarDelegate.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is DiscussionListEffect.ShowSnackbar -> {
                    snackbarState.showSnackbar(
                        state = SnackbarState.NEGATIVE,
                        message = effect.message,
                    )
                }

                DiscussionListEffect.ScrollToTop -> listState.animateScrollToItem(0)
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.shouldLoadNextPage() }
            .distinctUntilChanged()
            .collect { shouldLoad ->
                if (shouldLoad) viewModel.onIntent(DiscussionListIntent.LoadNextPage)
            }
    }

    DiscussionListScreen(
        uiState = uiState,
        listState = listState,
        isRefreshing = uiState.isRefreshing,
        onRefresh = { viewModel.onIntent(DiscussionListIntent.RefreshList) },
        onClickDiscussion = navigateToDiscussionDetail,
        onClickCreateDiscussion = navigateToCreateDiscussion,
        onClickTrackFilter = { track ->
            viewModel.onIntent(DiscussionListIntent.ClickTrackFilter(track))
        },
        onClickStatusFilter = { status ->
            viewModel.onIntent(DiscussionListIntent.ClickDiscussionStatusFilter(status))
        },
        onClickTypeFilter = { type ->
            viewModel.onIntent(DiscussionListIntent.ClickDiscussionTypeFilter(type))
        },
        modifier = modifier,
    )
}

@Composable
private fun DiscussionListScreen(
    uiState: DiscussionListState,
    listState: LazyListState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClickDiscussion: (discussionId: Long) -> Unit,
    onClickCreateDiscussion: () -> Unit,
    onClickTrackFilter: (track: TrackUiModel) -> Unit,
    onClickStatusFilter: (status: DiscussionStatusUiModel) -> Unit,
    onClickTypeFilter: (type: DiscussionTypeUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    var shouldShowFilterSection by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .distinctUntilChanged()
            .collect { isScrolling ->
                if (isScrolling && shouldShowFilterSection) {
                    shouldShowFilterSection = false
                }
            }
    }

    Column(modifier = modifier.fillMaxSize()) {
        DiscussionListTopAppBar(
            isFilterVisible = shouldShowFilterSection,
            onFilterClick = { shouldShowFilterSection = !shouldShowFilterSection },
        )

        DiscussionListFilterSection(
            visible = shouldShowFilterSection,
            filters = uiState.filter,
            onClickTrackFilter = onClickTrackFilter,
            onClickStatusFilter = onClickStatusFilter,
            onClickTypeFilter = onClickTypeFilter,
        )

        if (uiState.shouldShowEmptyView) {
            DiscussionListEmptyView(onClickCreateDiscussion = onClickCreateDiscussion)
        } else {
            DiscussionListSection(
                listState = listState,
                discussions = uiState.filteredDiscussions,
                onClickDiscussion = onClickDiscussion,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DiscussionListScreenPreview() {
    DialogTheme {
        Scaffold { innerPadding ->
            DiscussionListScreen(
                modifier = Modifier.padding(innerPadding),
                onClickCreateDiscussion = {},
                onClickDiscussion = {},
                onClickTrackFilter = {},
                onClickStatusFilter = {},
                onClickTypeFilter = {},
                listState = rememberLazyListState(),
                uiState = DiscussionListState(
                    discussions = List(4) {
                        if (it % 2 == 0) {
                            DiscussionUiModel.OnlineDiscussionUiModel(
                                id = it.toLong(),
                                title = "토론 제목 $it",
                                author = "작성자 $it",
                                track = TrackUiModel.entries[0],
                                status = DiscussionStatusUiModel.entries[0],
                                commentCount = 3,
                                period = "~ 2025.03.01",
                            )
                        } else {
                            DiscussionUiModel.OfflineDiscussionUiModel(
                                id = it.toLong(),
                                title = "토론 제목 $it",
                                author = "작성자 $it",
                                track = TrackUiModel.entries[0],
                                status = DiscussionStatusUiModel.entries[0],
                                commentCount = 5,
                                period = "2025.02.03 ~ 2025.03.01",
                                participantCapacity = "2/4",
                                place = "Zoom",
                            )
                        }
                    }.toImmutableList(),
                ),
                isRefreshing = false,
                onRefresh = {},
            )
        }
    }
}
