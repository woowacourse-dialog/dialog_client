package com.on.dialog.ui.component.markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.NavigationEventState
import androidx.navigationevent.compose.rememberNavigationEventState
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogIconButtonTone
import com.on.dialog.designsystem.component.DialogTextField
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.component.DecisionDialog
import com.on.dialog.ui.component.markdown.style.MarkdownStyle
import com.on.dialog.ui.component.markdown.style.MarkdownStyle.Companion.markdownStyles
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.markdown_editor_dialog_confirm
import dialog.core.ui.generated.resources.markdown_editor_dialog_content
import dialog.core.ui.generated.resources.markdown_editor_dialog_exit
import dialog.core.ui.generated.resources.markdown_editor_place_holder_please_enter_contents
import org.jetbrains.compose.resources.stringResource

enum class RendererTab {
    WRITE,
    PREVIEW,
}

@Composable
fun MarkdownEditor(
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
                selection = TextRange(initialContent.length),
            ),
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

    var selectedTab: RendererTab by rememberSaveable { mutableStateOf(RendererTab.WRITE) }

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
        onBackPress = handleBackPress,
        onConfirm = onConfirm,
        onExit = onExit,
        focusRequester = focusRequester,
        content = content,
        onContentChanged = { content = it },
        selectedTab = selectedTab,
        onSelectedTabChanged = { selectedTab = it },
        modifier = modifier,
    )
}

@Composable
private fun MarkdownEditor(
    onConfirm: (String) -> Unit,
    onExit: () -> Unit,
    onBackPress: () -> Unit,
    showExitDialog: Boolean,
    onShowExitDialog: (Boolean) -> Unit,
    focusRequester: FocusRequester,
    content: TextFieldValue,
    onContentChanged: (TextFieldValue) -> Unit,
    selectedTab: RendererTab,
    onSelectedTabChanged: (RendererTab) -> Unit,
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
        MarkdownEditorTopAppBar(
            onBackPress = onBackPress,
            onConfirm = { onConfirm(content.text) },
            selectedTab = selectedTab,
            onSelectedTabChanged = onSelectedTabChanged,
        )
        when (selectedTab) {
            RendererTab.WRITE -> {
                WriterView(content, onContentChanged, focusRequester, Modifier.weight(weight = 1f))
            }

            RendererTab.PREVIEW -> {
                RendererView(content.text, Modifier.weight(weight = 1f))
            }
        }
    }
}

@Composable
private fun RendererView(content: String, modifier: Modifier) {
    DialogMarkdown(
        content = content,
        modifier = modifier
            .fillMaxWidth()
            .background(color = DialogTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3F))
            .padding(all = DialogTheme.spacing.large),
    )
}

@Composable
private fun WriterView(
    content: TextFieldValue,
    onContentChanged: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier,
) {
    DialogTextField(
        value = content,
        onValueChange = { newValue ->
            val newContent = handleNewLine(
                newValue = newValue,
                currentContent = content,
            )
            onContentChanged(newContent)
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        singleLine = false,
        placeholder = stringResource(resource = Res.string.markdown_editor_place_holder_please_enter_contents),
        cornerBasedShape = null,
    )
    MarkdownButtons(content = content, onContentChanged = onContentChanged)
}

@Composable
private fun MarkdownButtons(
    content: TextFieldValue,
    onContentChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = DialogTheme.spacing.small),
        contentPadding = PaddingValues(
            horizontal = DialogTheme.spacing.large,
            vertical = DialogTheme.spacing.extraSmall,
        ),
        modifier = modifier.windowInsetsPadding(insets = WindowInsets.ime),
    ) {
        items(count = markdownStyles.size) { index ->
            DialogIconButton(
                onClick = { onContentChanged(markdownStyles[index].transform(content)) },
                content = {
                    Icon(
                        modifier = Modifier.fillMaxSize(0.6f),
                        imageVector = markdownStyles[index].icon,
                        contentDescription = null,
                    )
                },
                tone = DialogIconButtonTone.Primary,
            )
        }
    }
}

private fun handleNewLine(
    newValue: TextFieldValue,
    currentContent: TextFieldValue,
): TextFieldValue {
    val isNewLineAdded = newValue.text.length > currentContent.text.length &&
        newValue.text.substring(startIndex = currentContent.text.length).contains(char = '\n')

    if (!isNewLineAdded) {
        return newValue
    }

    return MarkdownStyle.Number.handleNewLine(newValue) ?: newValue
}

private data class MarkdownEditorPreviewParams(
    val isDark: Boolean,
    val showExitDialog: Boolean,
    val selectedTab: RendererTab,
)

private class MarkdownEditorPreviewProvider : PreviewParameterProvider<MarkdownEditorPreviewParams> {
    override val values = sequenceOf(
        MarkdownEditorPreviewParams(
            isDark = false,
            showExitDialog = false,
            selectedTab = RendererTab.WRITE,
        ),
        MarkdownEditorPreviewParams(
            isDark = true,
            showExitDialog = false,
            selectedTab = RendererTab.WRITE,
        ),
        MarkdownEditorPreviewParams(
            isDark = false,
            showExitDialog = true,
            selectedTab = RendererTab.WRITE,
        ),
        MarkdownEditorPreviewParams(
            isDark = true,
            showExitDialog = true,
            selectedTab = RendererTab.WRITE,
        ),
        MarkdownEditorPreviewParams(
            isDark = false,
            showExitDialog = false,
            selectedTab = RendererTab.PREVIEW,
        ),
        MarkdownEditorPreviewParams(
            isDark = true,
            showExitDialog = false,
            selectedTab = RendererTab.PREVIEW,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun MarkdownEditorPreview(
    @PreviewParameter(MarkdownEditorPreviewProvider::class) params: MarkdownEditorPreviewParams,
) {
    DialogTheme(darkTheme = params.isDark) {
        MarkdownEditorPreviewContent(
            showExitDialog = params.showExitDialog,
            selectedTab = params.selectedTab,
        )
    }
}

@Composable
private fun MarkdownEditorPreviewContent(
    showExitDialog: Boolean,
    selectedTab: RendererTab = RendererTab.WRITE,
) {
    val focusRequester: FocusRequester = remember { FocusRequester() }

    MarkdownEditor(
        showExitDialog = showExitDialog,
        onShowExitDialog = {},
        onBackPress = {},
        onConfirm = {},
        onExit = {},
        focusRequester = focusRequester,
        content = TextFieldValue("# Hello World\n\nThis is a **bold** statement."),
        onContentChanged = {},
        selectedTab = selectedTab,
        onSelectedTabChanged = {},
    )
}
