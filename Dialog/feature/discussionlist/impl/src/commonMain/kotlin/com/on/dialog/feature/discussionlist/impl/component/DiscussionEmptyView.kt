package com.on.dialog.feature.discussionlist.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.feature.discussionlist.impl.generated.resources.Res
import dialog.feature.discussionlist.impl.generated.resources.discussion_list_empty_view_body
import dialog.feature.discussionlist.impl.generated.resources.discussion_list_empty_view_create_discussion_button
import dialog.feature.discussionlist.impl.generated.resources.discussion_list_empty_view_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionListEmptyView(
    modifier: Modifier = Modifier,
    onClickCreateDiscussion: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = DialogTheme.spacing.large),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = DialogIcons.Forum,
            contentDescription = null,
            modifier = Modifier.size(56.dp),
            tint = DialogTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(DialogTheme.spacing.medium))

        Text(
            text = stringResource(Res.string.discussion_list_empty_view_title),
            style = DialogTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(DialogTheme.spacing.extraSmall))

        Text(
            text = stringResource(Res.string.discussion_list_empty_view_body),
            style = DialogTheme.typography.bodyMedium,
            color = DialogTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(DialogTheme.spacing.large))

        DialogButton(
            text = stringResource(Res.string.discussion_list_empty_view_create_discussion_button),
            onClick = onClickCreateDiscussion,
        )
    }
}

@Preview
@Composable
private fun DiscussionListEmptyViewPreview() {
    DialogTheme {
        Scaffold { innerPadding ->
            DiscussionListEmptyView(
                modifier = Modifier.padding(innerPadding),
                onClickCreateDiscussion = {},
            )
        }
    }
}
