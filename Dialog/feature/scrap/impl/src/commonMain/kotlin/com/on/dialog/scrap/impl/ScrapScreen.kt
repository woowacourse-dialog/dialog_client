package com.on.dialog.scrap.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.component.LoadingIndicator
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.scrap.impl.component.DiscussionListSection
import com.on.dialog.scrap.impl.model.DiscussionStatusUiModel
import com.on.dialog.scrap.impl.model.ScrapUiModel
import com.on.dialog.scrap.impl.model.TrackUiModel
import com.on.dialog.scrap.impl.viewmodel.ScrapEffect
import com.on.dialog.scrap.impl.viewmodel.ScrapIntent
import com.on.dialog.scrap.impl.viewmodel.ScrapState
import com.on.dialog.scrap.impl.viewmodel.ScrapViewModel
import com.on.dialog.ui.component.CommonStateAction
import com.on.dialog.ui.component.CommonStateView
import com.on.dialog.ui.extensions.shouldLoadNextPage
import com.on.dialog.ui.state.LocalAppLoginStateHolder
import dialog.feature.scrap.impl.generated.resources.Res
import dialog.feature.scrap.impl.generated.resources.empty_description
import dialog.feature.scrap.impl.generated.resources.empty_title
import dialog.feature.scrap.impl.generated.resources.top_app_bar_title
import dialog.feature.scrap.impl.generated.resources.unauthorized_primary_action
import dialog.feature.scrap.impl.generated.resources.unauthorized_title
import dialog.feature.scrap.impl.generated.resources.unauthorized_title_description
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ScrapScreen(
    navigateToDetail: (discussionId: Long) -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScrapViewModel = koinViewModel(),
) {
    val uiState: ScrapState by viewModel.uiState.collectAsStateWithLifecycle()
    val appLoginStateHolder = LocalAppLoginStateHolder.current
    val isLoggedIn by appLoginStateHolder.isLoggedIn.collectAsStateWithLifecycle()
    val snackbarState = LocalSnackbarDelegate.current
    val listState: LazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ScrapEffect.ShowSnackbar -> snackbarState.showSnackbar(
                    message = getString(effect.message),
                    state = effect.state,
                )
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.shouldLoadNextPage() }
            .distinctUntilChanged()
            .collect { shouldLoad ->
                if (shouldLoad) viewModel.onIntent(ScrapIntent.LoadNextPage)
            }
    }

    LaunchedEffect(isLoggedIn) {
        viewModel.onIntent(ScrapIntent.LoginStatusChanged(isLoggedIn = isLoggedIn))
    }

    ScrapScreen(
        uiState = uiState,
        listState = listState,
        isRefreshing = uiState is ScrapState.Loading,
        onRefresh = { viewModel.onIntent(ScrapIntent.Refresh) },
        onClickNavigateToLogin = navigateToLogin,
        onClickDiscussion = navigateToDetail,
        modifier = modifier,
    )
}

@Composable
private fun ScrapScreen(
    uiState: ScrapState,
    listState: LazyListState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onClickNavigateToLogin: () -> Unit,
    onClickDiscussion: (discussionId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        ScrapScreenTopAppBar()
        Box {
            when (uiState) {
                is ScrapState.Empty -> {
                    CommonStateView(
                        title = stringResource(Res.string.empty_title),
                        description = stringResource(Res.string.empty_description),
                        icon = DialogIcons.BookmarkBorder,
                    )
                }

                is ScrapState.Loading,
                is ScrapState.Content,
                -> {
                    DiscussionListSection(
                        listState = listState,
                        discussions = uiState.scraps,
                        onClickDiscussion = onClickDiscussion,
                        isRefreshing = isRefreshing,
                        onRefresh = onRefresh,
                    )
                    if (uiState is ScrapState.Loading) LoadingIndicator()
                }

                is ScrapState.UnAuthorized -> {
                    CommonStateView(
                        title = stringResource(Res.string.unauthorized_title),
                        description = stringResource(Res.string.unauthorized_title_description),
                        primaryAction = CommonStateAction(
                            label = stringResource(Res.string.unauthorized_primary_action),
                            onClick = onClickNavigateToLogin,
                        ),
                        icon = DialogIcons.Unauthorized,
                    )
                }
            }
        }
    }
}

@Composable
private fun ScrapScreenTopAppBar(
    modifier: Modifier = Modifier,
) {
    DialogTopAppBar(
        title = stringResource(Res.string.top_app_bar_title),
        modifier = modifier,
    )
}

@Composable
@ThemePreview
private fun ScrapScreenPreview() {
    DialogTheme {
        Scaffold {
            ScrapScreen(
                uiState = ScrapState.Content(
                    scraps = List(5) {
                        if (it % 2 == 0) {
                            ScrapUiModel.OnlineScrapUiModel(
                                id = it.toLong(),
                                title = "토론 제목 $it",
                                author = "작성자 $it",
                                track = TrackUiModel.entries.random(),
                                status = DiscussionStatusUiModel.entries.random(),
                                commentCount = 3,
                                period = "~ 2025.03.01",
                            )
                        } else {
                            ScrapUiModel.OfflineScrapUiModel(
                                id = it.toLong(),
                                title = "토론 제목 $it",
                                author = "작성자 $it",
                                track = TrackUiModel.entries.random(),
                                status = DiscussionStatusUiModel.entries.random(),
                                commentCount = 5,
                                period = "2025.02.03 ~ 2025.03.01",
                                participantCapacity = "2/4",
                                place = "Zoom",
                            )
                        }
                    }.toImmutableList(),
                ),
                listState = rememberLazyListState(),
                isRefreshing = false,
                onClickNavigateToLogin = {},
                onRefresh = {},
                onClickDiscussion = {},
            )
        }
    }
}

@Composable
@ThemePreview
private fun ScrapScreenEmptyPreview() {
    DialogTheme {
        Scaffold {
            ScrapScreen(
                uiState = ScrapState.Empty,
                listState = rememberLazyListState(),
                onClickNavigateToLogin = {},
                isRefreshing = false,
                onRefresh = {},
                onClickDiscussion = {},
            )
        }
    }
}

@Composable
@ThemePreview
private fun ScrapScreenUnauthorizedPreview() {
    DialogTheme {
        Scaffold {
            ScrapScreen(
                uiState = ScrapState.UnAuthorized,
                listState = rememberLazyListState(),
                onClickNavigateToLogin = {},
                isRefreshing = false,
                onRefresh = {},
                onClickDiscussion = {},
            )
        }
    }
}
