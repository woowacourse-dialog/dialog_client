package com.on.dialog.ui.component.markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.NavigationEventState
import androidx.navigationevent.compose.rememberNavigationEventState
import com.on.dialog.designsystem.component.DialogTextField
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.markdown_editor_place_holder_please_enter_contents
import org.jetbrains.compose.resources.stringResource

@Composable
fun MarkdownEditor(
    title: String,
    initialContent: TextFieldValue,
    onConfirm: (TextFieldValue) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val navState: NavigationEventState<NavigationEventInfo.None> =
        rememberNavigationEventState(NavigationEventInfo.None)
    val focusRequester: FocusRequester = remember { FocusRequester() }

    var content: TextFieldValue by remember { mutableStateOf(initialContent) }

    val markdownStyles: List<MarkdownStyle> =
        listOf(
            MarkdownStyle.Bold,
            MarkdownStyle.Italic,
            MarkdownStyle.Code,
            MarkdownStyle.Quote,
            MarkdownStyle.Bullet,
            MarkdownStyle.Number,
            MarkdownStyle.Link,
            MarkdownStyle.CodeBlock
        )

    NavigationBackHandler(
        state = navState,
        isBackEnabled = true,
        onBackCompleted = onExit
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DialogTheme.colorScheme.background)
    ) {
        DialogTopAppBar(
            title = title,
            navigationIcon = {
                IconButton(onClick = onExit) {
                    Icon(
                        imageVector = DialogIcons.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    onConfirm(content)
                    onExit()
                }) {
                    Icon(
                        imageVector = DialogIcons.Check,
                        contentDescription = null
                    )
                }
            },
            centerAligned = true
        )
        DialogTextField(
            value = content,
            onValueChange = { newValue ->
                if (newValue.text.length > content.text.length &&
                    newValue.text.substring(content.text.length).contains('\n')
                ) {
                    val handled: Boolean = MarkdownStyle.Number.handleNewLine(newValue) {
                        content = it
                    }
                    if (!handled) {
                        content = newValue
                    }
                } else {
                    content = newValue
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .focusRequester(focusRequester),
            singleLine = false,
            placeholder = stringResource(Res.string.markdown_editor_place_holder_please_enter_contents)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 5.dp),
            modifier = Modifier.windowInsetsPadding(WindowInsets.ime)
        ) {
            items(markdownStyles.size) { index ->
                MarkdownButton(
                    style = markdownStyles[index],
                    content = content,
                    onContentChanged = { content = it }
                )
            }
        }
    }
}