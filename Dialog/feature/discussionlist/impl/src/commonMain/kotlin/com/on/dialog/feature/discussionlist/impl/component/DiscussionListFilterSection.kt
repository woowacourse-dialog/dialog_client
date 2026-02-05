package com.on.dialog.feature.discussionlist.impl.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogChip
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.theme.dropShadow
import com.on.dialog.feature.discussionlist.impl.model.DiscussionStatusUiModel
import com.on.dialog.feature.discussionlist.impl.model.DiscussionTypeUiModel
import com.on.dialog.feature.discussionlist.impl.model.SelectedFilters
import com.on.dialog.feature.discussionlist.impl.model.TrackUiModel
import dialog.feature.discussionlist.impl.generated.resources.Res
import dialog.feature.discussionlist.impl.generated.resources.discussion_list_status_filter_title
import dialog.feature.discussionlist.impl.generated.resources.discussion_list_track_filter_title
import dialog.feature.discussionlist.impl.generated.resources.discussion_list_type_filter_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionListFilterSection(
    visible: Boolean,
    filters: SelectedFilters,
    onClickTrackFilter: (track: TrackUiModel) -> Unit,
    onClickStatusFilter: (status: DiscussionStatusUiModel) -> Unit,
    onClickTypeFilter: (type: DiscussionTypeUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier
            .dropShadow(RectangleShape)
            .background(DialogTheme.colorScheme.surface),
        enter = fadeIn() + expandVertically(),
        exit = shrinkVertically() + fadeOut(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DialogTheme.spacing.large)
                .padding(top = DialogTheme.spacing.extraSmall, bottom = DialogTheme.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small),
        ) {
            FilterSection(
                title = stringResource(Res.string.discussion_list_track_filter_title),
            ) {
                items(
                    items = TrackUiModel.entries,
                    key = { track -> track.title },
                ) { track ->
                    FilterToggleChip(
                        text = track.title,
                        selected = filters.selectedTrackFilter.contains(track),
                        onClick = { onClickTrackFilter(track) },
                    )
                }
            }

            FilterSection(
                title = stringResource(Res.string.discussion_list_status_filter_title),
            ) {
                items(
                    items = DiscussionStatusUiModel.entries,
                    key = { status -> status.name },
                ) { status ->
                    FilterToggleChip(
                        text = status.title,
                        selected = filters.selectedStatusFilter.contains(status),
                        onClick = { onClickStatusFilter(status) },
                    )
                }
            }

            FilterSection(
                title = stringResource(Res.string.discussion_list_type_filter_title),
            ) {
                items(
                    items = DiscussionTypeUiModel.entries,
                    key = { type -> type.name },
                ) { type ->
                    FilterToggleChip(
                        text = type.title,
                        selected = filters.selectedTypeFilter.contains(type),
                        onClick = { onClickTypeFilter(type) },
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterSection(
    title: String,
    modifier: Modifier = Modifier,
    items: LazyListScope.() -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = DialogTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.width(DialogTheme.spacing.medium))
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.extraSmall),
            content = items,
        )
    }
}

@Composable
private fun FilterToggleChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogChip(
        text = text,
        modifier = modifier
            .clip(DialogTheme.shapes.large)
            .clickable(
                indication = ripple(),
                interactionSource = null,
                onClick = onClick,
            ).border(
                width = if (selected) 0.dp else 0.5.dp,
                shape = DialogTheme.shapes.large,
                color = DialogTheme.colorScheme.outline,
            ),
        textColor = if (selected) DialogTheme.colorScheme.onPrimary else DialogTheme.colorScheme.primary,
        backgroundColor = if (selected) DialogTheme.colorScheme.primary else DialogTheme.colorScheme.onPrimary,
    )
}

@Preview
@Composable
private fun FilterToggleChipPreview() {
    DialogTheme {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            FilterToggleChip(
                text = "Filter",
                selected = false,
                onClick = {},
            )
            FilterToggleChip(
                text = "Filter",
                selected = true,
                onClick = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscussionListFilterSectionPreview() {
    DialogTheme {
        DiscussionListFilterSection(
            visible = true,
            filters = SelectedFilters(
                selectedTrackFilter = listOf(TrackUiModel.ANDROID),
                selectedStatusFilter = listOf(DiscussionStatusUiModel.RECRUITING),
                selectedTypeFilter = listOf(DiscussionTypeUiModel.ONLINE),
            ),
            onClickTrackFilter = {},
            onClickStatusFilter = {},
            onClickTypeFilter = {},
        )
    }
}
