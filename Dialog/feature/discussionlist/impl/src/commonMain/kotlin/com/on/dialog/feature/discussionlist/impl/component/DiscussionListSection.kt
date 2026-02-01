package com.on.dialog.feature.discussionlist.impl.component

import androidx.compose.foundation.ScrollIndicatorState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.util.drawFadingEdges
import com.on.dialog.feature.discussionlist.impl.model.DiscussionUiModel
import com.on.dialog.ui.component.DiscussionCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

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
        item { Spacer(modifier = Modifier.height(DialogTheme.spacing.small)) }
        items(items = discussions, key = { discussion -> discussion.id }) { discussion ->
            DiscussionCard(
                chips = discussion.toChipCategories().toImmutableList(),
                title = discussion.title,
                author = discussion.author,
                endAt = discussion.modifiedAt.toString(),
                discussionCount = discussion.commentCount,
                onClick = { onClickDiscussion(discussion.id) },
            )
        }
        item { Spacer(modifier = Modifier.height(DialogTheme.spacing.large)) }
    }
}
