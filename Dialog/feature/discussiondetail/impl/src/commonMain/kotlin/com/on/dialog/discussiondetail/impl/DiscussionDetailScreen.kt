package com.on.dialog.discussiondetail.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.component.markdown.MarkdownEditor

@Composable
fun DiscussionDetailScreen(
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var content by rememberSaveable {
        mutableStateOf("")
    }
    var showMarkdownEditor by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        DialogTopAppBar(
            title = "토론 상세 화면",
            centerAligned = false,
            navigationIcon = {
                DialogIconButton(onClick = goBack) {
                    Icon(imageVector = DialogIcons.ArrowBack, contentDescription = null)
                }
            },
        )

        CommentInputPlaceholder(
            onClick = { showMarkdownEditor = true },
            modifier = Modifier.padding(top = DialogTheme.spacing.medium),
        )
    }

    if (showMarkdownEditor) {
        MarkdownEditor(
            initialContent = content,
            onConfirm = { newContent: String -> content = newContent },
            onExit = { showMarkdownEditor = false },
        )
    }
}

@Composable
private fun CommentInputPlaceholder(
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
            )
            .padding(
                horizontal = DialogTheme.spacing.medium,
                vertical = DialogTheme.spacing.large,
            )
    ) {
        Text(
            text = "댓글을 작성해 주세요",
            style = DialogTheme.typography.bodyMedium,
            color = DialogTheme.colorScheme.onSurfaceVariant,
        )
    }
}


@Composable
@Preview(showBackground = true)
private fun DiscussionDetailScreenPreview() {
    DialogTheme {
        DiscussionDetailScreen({})
    }
}
