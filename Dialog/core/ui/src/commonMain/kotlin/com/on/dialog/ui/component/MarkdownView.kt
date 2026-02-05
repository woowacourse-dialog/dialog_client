package com.on.dialog.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.ui.component.button.MarkdownButton
import com.on.dialog.ui.component.button.MarkdownStyle

@Composable
fun MarkdownView(
    title: String,
    content: TextFieldValue,
    onContentChanged: (TextFieldValue) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        DialogTopAppBar(
            title = title,
            navigationIcon = {
                Icon(
                    imageVector = DialogIcons.ArrowBack,
                    contentDescription = null
                )
            },
            actions = {
                Icon(
                    imageVector = DialogIcons.Check,
                    contentDescription = null
                )
            },
            centerAligned = true
        )
        TextField(
            value = content,
            onValueChange = onContentChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 5.dp),
        ) {
            item {
                MarkdownButton(
                    style = MarkdownStyle.Bold,
                    icon = DialogIcons.bold,
                    content = content,
                    onContentChanged = onContentChanged
                )
            }

            item {
                MarkdownButton(
                    style = MarkdownStyle.Italic,
                    icon = DialogIcons.italic,
                    content = content,
                    onContentChanged = onContentChanged
                )
            }

            item {
                MarkdownButton(
                    style = MarkdownStyle.Code,
                    icon = DialogIcons.code,
                    content = content,
                    onContentChanged = onContentChanged
                )
            }

            item {
                MarkdownButton(
                    style = MarkdownStyle.CodeBlock,
                    icon = DialogIcons.codeBlock,
                    content = content,
                    onContentChanged = onContentChanged
                )
            }

            item {
                MarkdownButton(
                    style = MarkdownStyle.Bullet,
                    icon = DialogIcons.bullet,
                    content = content,
                    onContentChanged = onContentChanged
                )
            }

            item {
                MarkdownButton(
                    style = MarkdownStyle.Link,
                    icon = DialogIcons.link,
                    content = content,
                    onContentChanged = onContentChanged
                )
            }

            item {
                MarkdownButton(
                    style = MarkdownStyle.Quote,
                    icon = DialogIcons.quote,
                    content = content,
                    onContentChanged = onContentChanged
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarkdownViewPreview() {
    var text by remember {
        mutableStateOf(TextFieldValue("여기에 입력"))
    }

    MarkdownView(
        title = "댓글 작성",
        content = text,
        onContentChanged = {
            text = it
        },
        onExit = {}
    )
}
