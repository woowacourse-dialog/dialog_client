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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.NavigationEventState
import androidx.navigationevent.compose.rememberNavigationEventState
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogTextField
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.component.markdown.MarkdownStyle.Companion.markdownStyles
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.markdown_editor_dialog_confirm
import dialog.core.ui.generated.resources.markdown_editor_dialog_content
import dialog.core.ui.generated.resources.markdown_editor_dialog_exit
import dialog.core.ui.generated.resources.markdown_editor_place_holder_please_enter_contents
import org.jetbrains.compose.resources.stringResource

@Composable
fun MarkdownEditor(
    title: String,
    initialContent: String,
    onConfirm: (String) -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val navState: NavigationEventState<NavigationEventInfo.None> =
        rememberNavigationEventState(NavigationEventInfo.None)
    val focusRequester: FocusRequester = remember { FocusRequester() }
    var content by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                text = initialContent,
                selection = TextRange(initialContent.length)
            )
        )
    }
    var showExitDialog by rememberSaveable { mutableStateOf(false) }

    NavigationBackHandler(
        state = navState,
        isBackEnabled = true,
        onBackCompleted = {
            if (content.text == initialContent) onExit() else showExitDialog = true
        },
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            text = { Text(text = stringResource(Res.string.markdown_editor_dialog_content)) },
            confirmButton = { TextButton(onClick = onExit) { Text(stringResource(Res.string.markdown_editor_dialog_confirm)) } },
            dismissButton = {
                TextButton(onClick = {
                    showExitDialog = false
                }) { Text(stringResource(Res.string.markdown_editor_dialog_exit)) }
            },
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DialogTheme.colorScheme.background),
    ) {
        DialogTopAppBar(
            title = title,
            navigationIcon = {
                DialogIconButton(onClick = {
                    if (content.text == initialContent) onExit() else showExitDialog = true
                }) {
                    Icon(
                        imageVector = DialogIcons.ArrowBack,
                        contentDescription = null,
                    )
                }
            },
            actions = {
                DialogIconButton(onClick = {
                    onConfirm(content.text)
                    onExit()
                }) {
                    Icon(
                        imageVector = DialogIcons.Check,
                        contentDescription = null,
                    )
                }
            },
            centerAligned = true,
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
            placeholder = stringResource(Res.string.markdown_editor_place_holder_please_enter_contents),
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 5.dp),
            modifier = Modifier.windowInsetsPadding(WindowInsets.ime),
        ) {
            items(markdownStyles.size) { index ->
                MarkdownButton(
                    style = markdownStyles[index],
                    content = content,
                    onContentChanged = { content = it },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MarkdownEditorPreview() {
    DialogTheme {
        MarkdownEditor(
            title = "Title",
            initialContent = "",
            onConfirm = {},
            onExit = {},
        )
    }
}
