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
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.NavigationEventState
import androidx.navigationevent.compose.rememberNavigationEventState
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogTextField
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.component.DecisionDialog
import com.on.dialog.ui.component.markdown.style.MarkdownStyle
import com.on.dialog.ui.component.markdown.style.MarkdownStyle.Companion.markdownStyles
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.markdown_editor_back
import dialog.core.ui.generated.resources.markdown_editor_confirm
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
        rememberNavigationEventState(currentInfo = NavigationEventInfo.None)
    val focusRequester: FocusRequester = remember { FocusRequester() }
    var content: TextFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                text = initialContent,
                selection = TextRange(initialContent.length)
            )
        )
    }
    var showExitDialog: Boolean by rememberSaveable { mutableStateOf(false) }

    val handleBackPress: () -> Unit = remember {
        {
            if (content.text == initialContent) {
                onExit()
            } else {
                showExitDialog = true
            }
        }
    }

    NavigationBackHandler(
        state = navState,
        isBackEnabled = true,
        onBackCompleted = {
            handleBackPress()
        },
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    MarkdownEditor(
        showExitDialog = showExitDialog,
        onShowExitDialog = { showExitDialog = it },
        title = title,
        onBackPress = handleBackPress,
        onConfirm = onConfirm,
        onExit = onExit,
        focusRequester = focusRequester,
        content = content,
        onContentChanged = { content = it },
        modifier = modifier,
    )
}

@Composable
private fun MarkdownEditor(
    title: String,
    onConfirm: (String) -> Unit,
    onExit: () -> Unit,
    onBackPress: () -> Unit,
    showExitDialog: Boolean,
    onShowExitDialog: (Boolean) -> Unit,
    focusRequester: FocusRequester,
    content: TextFieldValue,
    onContentChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (showExitDialog) {
        DecisionDialog(
            contentText = stringResource(resource = Res.string.markdown_editor_dialog_content),
            confirmText = stringResource(resource = Res.string.markdown_editor_dialog_confirm),
            onConfirm = onExit,
            dismissText = stringResource(resource = Res.string.markdown_editor_dialog_exit),
            onDismiss = { onShowExitDialog(false) },
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = DialogTheme.colorScheme.background),
    ) {
        DialogTopAppBar(
            title = title,
            navigationIcon = {
                DialogIconButton(onClick = onBackPress) {
                    Icon(
                        imageVector = DialogIcons.ArrowBack,
                        contentDescription = stringResource(resource = Res.string.markdown_editor_back),
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
                        contentDescription = stringResource(resource = Res.string.markdown_editor_confirm),
                    )
                }
            },
            centerAligned = true,
        )
        DialogTextField(
            value = content,
            onValueChange = { newValue -> isNewLineAppended(newValue, content, onContentChanged) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f)
                .focusRequester(focusRequester),
            singleLine = false,
            placeholder = stringResource(resource = Res.string.markdown_editor_place_holder_please_enter_contents),
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = DialogTheme.spacing.small),
            contentPadding = PaddingValues(
                horizontal = DialogTheme.spacing.large,
                vertical = DialogTheme.spacing.extraSmall
            ),
            modifier = Modifier.windowInsetsPadding(insets = WindowInsets.ime),
        ) {
            items(count = markdownStyles.size) { index ->
                MarkdownButton(
                    style = markdownStyles[index],
                    content = content,
                    onContentChanged = { onContentChanged(it) },
                )
            }
        }
    }
}

private fun isNewLineAppended(
    newValue: TextFieldValue,
    content: TextFieldValue,
    onContentChanged: (TextFieldValue) -> Unit,
) {
    if (newValue.text.length > content.text.length &&
        newValue.text.substring(startIndex = content.text.length).contains(char = '\n')
    ) {
        val handled: Boolean = MarkdownStyle.Number.handleNewLine(newValue) { onContentChanged(it) }
        if (!handled) {
            onContentChanged(newValue)
        }
    } else {
        onContentChanged(newValue)
    }
}

@Preview(showBackground = true, name = "Light - Normal")
@Composable
private fun MarkdownEditorPreviewLight() {
    DialogTheme {
        MarkdownEditorPreviewContent(showExitDialog = false)
    }
}

@Preview(showBackground = true, name = "Dark - Normal")
@Composable
private fun MarkdownEditorPreviewDark() {
    DialogTheme(darkTheme = true) {
        MarkdownEditorPreviewContent(showExitDialog = false)
    }
}

@Preview(showBackground = true, name = "Light - Exit Dialog")
@Composable
private fun MarkdownEditorExitDialogPreviewLight() {
    DialogTheme {
        MarkdownEditorPreviewContent(showExitDialog = true)
    }
}

@Preview(showBackground = true, name = "Dark - Exit Dialog")
@Composable
private fun MarkdownEditorExitDialogPreviewDark() {
    DialogTheme(darkTheme = true) {
        MarkdownEditorPreviewContent(showExitDialog = true)
    }
}

@Composable
private fun MarkdownEditorPreviewContent(
    showExitDialog: Boolean,
) {
    val focusRequester: FocusRequester = remember { FocusRequester() }

    MarkdownEditor(
        showExitDialog = showExitDialog,
        onShowExitDialog = {},
        title = "Title",
        onBackPress = {},
        onConfirm = {},
        onExit = {},
        focusRequester = focusRequester,
        content = TextFieldValue("내용물들~~~"),
        onContentChanged = {},
    )
}

