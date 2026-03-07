package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.component.markdown.DialogMarkdown
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.comment_placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CommentInputPlaceholder(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = DialogTheme.spacing.large)
            .clip(DialogTheme.shapes.medium)
            .clickable(onClick = onClick)
            .background(
                color = DialogTheme.colorScheme.surfaceVariant,
                shape = DialogTheme.shapes.medium,
            ).padding(
                horizontal = DialogTheme.spacing.medium,
                vertical = DialogTheme.spacing.large,
            ),
    ) {
        DialogMarkdown(
            content = text.ifBlank { stringResource(Res.string.comment_placeholder) },
            modifier = Modifier.wrapContentHeight(),
        )
    }
}
