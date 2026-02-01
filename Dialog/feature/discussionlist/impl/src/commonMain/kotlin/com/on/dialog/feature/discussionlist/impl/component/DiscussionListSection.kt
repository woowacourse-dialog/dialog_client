package com.on.dialog.feature.discussionlist.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.util.drawFadingEdges
import com.on.dialog.feature.discussionlist.impl.model.DiscussionStatusUiModel
import com.on.dialog.feature.discussionlist.impl.model.DiscussionUiModel
import com.on.dialog.feature.discussionlist.impl.model.TrackUiModel
import com.on.dialog.ui.component.DiscussionCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun DiscussionListSection(
    listState: LazyListState,
    discussions: ImmutableList<DiscussionUiModel>,
    onClickDiscussion: (discussionId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .drawFadingEdges(listState, DialogTheme.colorScheme.surface)
            .padding(horizontal = DialogTheme.spacing.large),
        state = listState,
        overscrollEffect = null,
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
    ) {
        item { Spacer(modifier = Modifier.height(DialogTheme.spacing.extraSmall)) }
        items(items = discussions, key = { discussion -> discussion.id }) { discussion ->
            DiscussionCard(
                chips = discussion.toChipCategories(),
                title = discussion.title,
                author = discussion.author,
                period = discussion.period,
                discussionCount = discussion.commentCount,
                participant = if (discussion is DiscussionUiModel.OfflineDiscussionUiModel) discussion.partingCapacity else null,
                onClick = { onClickDiscussion(discussion.id) },
            )
        }
        item { Spacer(modifier = Modifier.height(DialogTheme.spacing.large)) }
    }
}

@Preview
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
                        status = DiscussionStatusUiModel.INDISCUSSION,
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
                        partingCapacity = "2/4",
                        place = "Seoul",
                    ),
                ),
                onClickDiscussion = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
