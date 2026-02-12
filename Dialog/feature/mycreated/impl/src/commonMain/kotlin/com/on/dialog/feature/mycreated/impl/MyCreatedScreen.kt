package com.on.dialog.feature.mycreated.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.component.snackbar.LocalSnackbarDelegate
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.mycreated.impl.component.DiscussionListSection
import com.on.dialog.feature.mycreated.impl.model.DiscussionStatusUiModel
import com.on.dialog.feature.mycreated.impl.model.DiscussionUiModel
import com.on.dialog.feature.mycreated.impl.model.TrackUiModel
import com.on.dialog.feature.mycreated.impl.viewmodel.MyCreatedEffect
import com.on.dialog.feature.mycreated.impl.viewmodel.MyCreatedIntent
import com.on.dialog.feature.mycreated.impl.viewmodel.MyCreatedState
import com.on.dialog.feature.mycreated.impl.viewmodel.MyCreatedViewModel
import com.on.dialog.ui.component.CommonEmptyView
import com.on.dialog.ui.component.EmptyAction
import com.on.dialog.ui.extensions.shouldLoadNextPage
import dialog.feature.mycreated.impl.generated.resources.Res
import dialog.feature.mycreated.impl.generated.resources.empty_action_label
import dialog.feature.mycreated.impl.generated.resources.empty_description
import dialog.feature.mycreated.impl.generated.resources.empty_title
import dialog.feature.mycreated.impl.generated.resources.top_app_bar_title
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MyCreatedScreen(
    navigateToDetail: (discussionId: Long) -> Unit,
    navigateToCreateDiscussion: () -> Unit,
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyCreatedViewModel = koinViewModel(),
) {
    val uiState: MyCreatedState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarState = LocalSnackbarDelegate.current
    val listState: LazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyCreatedEffect.ShowSnackbar -> snackbarState.showSnackbar(
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
                if (shouldLoad) viewModel.onIntent(MyCreatedIntent.LoadNextPage)
            }
    }

    MyCreatedScreen(
        uiState = uiState,
        listState = listState,
        onClickDiscussion = navigateToDetail,
        onClickCreateDiscussion = navigateToCreateDiscussion,
        onBackClick = goBack,
        modifier = modifier,
    )
}

@Composable
private fun MyCreatedScreen(
    uiState: MyCreatedState,
    listState: LazyListState,
    onClickDiscussion: (discussionId: Long) -> Unit,
    onClickCreateDiscussion: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        MyCreatedTopAppBar(onBackClick = onBackClick)

        when (uiState) {
            is MyCreatedState.Empty -> {
                CommonEmptyView(
                    title = stringResource(Res.string.empty_title),
                    description = stringResource(Res.string.empty_description),
                    primaryAction = EmptyAction(
                        label = stringResource(Res.string.empty_action_label),
                        onClick = onClickCreateDiscussion,
                    ),
                )
            }

            is MyCreatedState.Loading,
            is MyCreatedState.Content,
            -> {
                DiscussionListSection(
                    listState = listState,
                    discussions = uiState.discussions,
                    onClickDiscussion = onClickDiscussion,
                )
            }
        }
    }
}

@Composable
private fun MyCreatedTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogTopAppBar(
        title = stringResource(Res.string.top_app_bar_title),
        modifier = modifier,
        navigationIcon = {
            DialogIconButton(onClick = onBackClick) {
                Icon(
                    imageVector = DialogIcons.ArrowBack,
                    contentDescription = null,
                )
            }
        },
    )
}

@Composable
@ThemePreview
private fun MyCreatedScreenPreview() {
    DialogTheme {
        Scaffold {
            MyCreatedScreen(
                uiState = MyCreatedState.Content(
                    discussions = List(5) {
                        if (it % 2 == 0) {
                            DiscussionUiModel.OnlineDiscussionUiModel(
                                id = it.toLong(),
                                title = "토론 제목 $it",
                                author = "작성자 $it",
                                track = TrackUiModel.entries.random(),
                                status = DiscussionStatusUiModel.entries.random(),
                                commentCount = 3,
                                period = "~ 2025.03.01",
                            )
                        } else {
                            DiscussionUiModel.OfflineDiscussionUiModel(
                                id = it.toLong(),
                                title = "토론 제목 $it",
                                author = "작성자 $it",
                                track = TrackUiModel.entries.random(),
                                status = DiscussionStatusUiModel.entries.random(),
                                commentCount = 5,
                                period = "2025.02.03 ~ 2025.03.01",
                                partingCapacity = "2/4",
                                place = "Zoom",
                            )
                        }
                    }.toImmutableList(),
                ),
                listState = rememberLazyListState(),
                onClickDiscussion = {},
                onClickCreateDiscussion = {},
                onBackClick = {},
            )
        }
    }
}

@Composable
@ThemePreview
private fun MyCreatedScreenEmptyPreview() {
    DialogTheme {
        Scaffold {
            MyCreatedScreen(
                uiState = MyCreatedState.Empty,
                listState = rememberLazyListState(),
                onClickDiscussion = {},
                onClickCreateDiscussion = {},
                onBackClick = {},
            )
        }
    }
}
