package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.discussiondetail.impl.model.DiscussionDetailUiModel

@Composable
internal fun DiscussionDetailBottomBar(
    discussion: DiscussionDetailUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(DialogTheme.colorScheme.primary.copy(alpha = 0.1f))
            .padding(PaddingValues(DialogTheme.spacing.extraSmall)),
    ) {
        DialogIconButton(
            onClick = {},
        ) {
            Icon(
                imageVector = DialogIcons.Bookmark,
                contentDescription = "북마크",
            )
        }

        when (discussion) {
            is DiscussionDetailUiModel.OfflineDiscussionDetailUiModel -> {
                DialogButton(text = "참여하기", onClick = {})
            }

            is DiscussionDetailUiModel.OnlineDiscussionDetailUiModel -> {
            }
        }
    }
}
