package com.on.dialog.discussiondetail.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.component.markdown.MarkdownEditor
import com.on.dialog.ui.component.markdown.MarkdownRenderer

@Composable
fun DiscussionDetailScreen(
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var content by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var showMarkdownEditor by remember { mutableStateOf(false) }

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

        MarkdownRenderer(
            text = content.text,
            modifier = Modifier.size(500.dp, 300.dp),
            onClickContent = {
                showMarkdownEditor = true
            },
        )
    }

    if (showMarkdownEditor) {
        MarkdownEditor(
            title = "댓글 작성",
            initialContent = content,
            onConfirm = { newContent: TextFieldValue ->
                content = newContent
            },
            onExit = {
                showMarkdownEditor = false
            },
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
