package com.on.dialog.feature.mycreated.impl.component

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
import com.on.dialog.feature.mycreated.impl.model.DiscussionUiModel
import com.on.dialog.ui.component.DiscussionCard
import dialog.feature.mycreated.impl.generated.resources.Res
import dialog.feature.mycreated.impl.generated.resources.discussion_card_title_format
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

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
            .drawFadingEdges(scrollableState = listState)
            .padding(horizontal = DialogTheme.spacing.large),
        state = listState,
        overscrollEffect = null,
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
    ) {
        item { Spacer(modifier = Modifier.height(DialogTheme.spacing.extraSmall)) }
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
                participant = if (discussion is DiscussionUiModel.OfflineDiscussionUiModel) discussion.partingCapacity else null,
                onClick = { onClickDiscussion(discussion.id) },
            )
        }
        item { Spacer(modifier = Modifier.height(DialogTheme.spacing.large)) }
    }
}
