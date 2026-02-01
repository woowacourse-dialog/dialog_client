package com.on.dialog.feature.discussionlist.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.feature.discussionlist.impl.generated.resources.Res
import dialog.feature.discussionlist.impl.generated.resources.discussion_list_filter_action_icon_content_description
import dialog.feature.discussionlist.impl.generated.resources.discussion_list_top_app_bar_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun DiscussionListTopAppBar(
    isFilterVisible: Boolean,
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit,
) {
    DialogTopAppBar(
        modifier = modifier,
        title = stringResource(Res.string.discussion_list_top_app_bar_title),
        centerAligned = false,
        actions = {
            DialogIconButton(onClick = onFilterClick) {
                Icon(
                    imageVector = if (isFilterVisible) DialogIcons.FilterOff else DialogIcons.Filter,
                    contentDescription = stringResource(Res.string.discussion_list_filter_action_icon_content_description),
                )
            }
        },
    )
}

@Preview
@Composable
private fun DiscussionListTopAppBarPreview() {
    DialogTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            DiscussionListTopAppBar(
                isFilterVisible = true,
                onFilterClick = {},
            )

            DiscussionListTopAppBar(
                isFilterVisible = false,
                onFilterClick = {},
            )
        }
    }
}
