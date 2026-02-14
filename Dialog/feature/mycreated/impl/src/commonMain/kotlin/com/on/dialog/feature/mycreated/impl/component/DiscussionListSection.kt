package com.on.dialog.feature.mycreated.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.util.drawFadingEdges
import com.on.dialog.feature.mycreated.impl.model.DiscussionStatusUiModel
import com.on.dialog.feature.mycreated.impl.model.DiscussionUiModel
import com.on.dialog.feature.mycreated.impl.model.TrackUiModel
import com.on.dialog.ui.component.DiscussionCard
import dialog.feature.mycreated.impl.generated.resources.Res
import dialog.feature.mycreated.impl.generated.resources.discussion_card_title_format
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionListSection(
    listState: LazyListState,
    discussions: ImmutableList<DiscussionUiModel>,
    onClickDiscussion: (discussionId: Long) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier.fillMaxSize(),
    ) {
        DiscussionListSection(
            listState = listState,
            discussions = discussions,
            onClickDiscussion = onClickDiscussion,
        )
    }
}

@Composable
private fun DiscussionListSection(
    listState: LazyListState,
    discussions: ImmutableList<DiscussionUiModel>,
    onClickDiscussion: (discussionId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .drawFadingEdges(scrollableState = listState),
        state = listState,
        overscrollEffect = null,
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
        contentPadding = PaddingValues(DialogTheme.spacing.large),
    ) {
        items(items = discussions, key = { discussion -> discussion.id }) { discussion ->
            DiscussionCard(
                chips = discussion.toChipCategories(),
                title = stringResource(
                    Res.string.discussion_card_title_format,
                    discussion.title,
                    discussion.status.title,
                ),
                author = discussion.author,
                period = discussion.period,
                discussionCount = discussion.commentCount,
                participant = if (discussion is DiscussionUiModel.OfflineDiscussionUiModel) discussion.participantCapacity else null,
                onClick = { onClickDiscussion(discussion.id) },
            )
        }
    }
}

@ThemePreview
@Composable
private fun DiscussionListSectionPreview() {
    DialogTheme {
        Scaffold { innerPadding ->
            DiscussionListSection(
                listState = rememberLazyListState(),
                discussions = persistentListOf(
                    DiscussionUiModel.OnlineDiscussionUiModel(
                        id = 1,
                        title = "Jetpack Compose is awesome",
                        author = "Android Dev",
                        track = TrackUiModel.ANDROID,
                        status = DiscussionStatusUiModel.IN_DISCUSSION,
                        commentCount = 10,
                        period = "~ 2025.02.03",
                    ),
                    DiscussionUiModel.OfflineDiscussionUiModel(
                        id = 2,
                        title = "KMP is the future",
                        author = "Kotlin Dev",
                        track = TrackUiModel.COMMON,
                        status = DiscussionStatusUiModel.RECRUIT_COMPLETE,
                        commentCount = 20,
                        period = "2025.02.03 ~ 2025.03.31",
                        participantCapacity = "2/4",
                        place = "Seoul",
                    ),
                ),
                onClickDiscussion = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
