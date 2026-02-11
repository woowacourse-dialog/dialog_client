package com.on.dialog.ui.component.markdown

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.AnimatedTab
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.theme.dropShadow
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.markdown_editor_back
import dialog.core.ui.generated.resources.markdown_editor_confirm
import dialog.core.ui.generated.resources.markdown_renderer_preview
import dialog.core.ui.generated.resources.markdown_renderer_write
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkdownEditorTopAppBar(
    onBackPress: () -> Unit,
    onConfirm: () -> Unit,
    selectedTab: RendererTab,
    onSelectedTabChanged: (RendererTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier.dropShadow(RectangleShape),
        title = {
            SecondaryTabRow(
                selectedTabIndex = selectedTab.ordinal,
                indicator = {},
                divider = {},
                modifier = Modifier.wrapContentWidth(),
            ) {
                AnimatedTab(
                    title = stringResource(Res.string.markdown_renderer_write),
                    selected = selectedTab == RendererTab.WRITE,
                    onClick = { onSelectedTabChanged(RendererTab.WRITE) },
                    modifier = Modifier.padding(
                        horizontal = DialogTheme.spacing.extraSmall,
                        vertical = DialogTheme.spacing.small,
                    ),
                )

                AnimatedTab(
                    title = stringResource(Res.string.markdown_renderer_preview),
                    selected = selectedTab == RendererTab.PREVIEW,
                    onClick = { onSelectedTabChanged(RendererTab.PREVIEW) },
                    modifier = Modifier.padding(
                        horizontal = DialogTheme.spacing.extraSmall,
                        vertical = DialogTheme.spacing.small,
                    ),
                )
            }
        },
        expandedHeight = 52.dp,
        navigationIcon = {
            DialogIconButton(onClick = onBackPress) {
                Icon(
                    imageVector = DialogIcons.ArrowBack,
                    contentDescription = stringResource(resource = Res.string.markdown_editor_back),
                )
            }
        },
        actions = {
            DialogIconButton(onClick = onConfirm) {
                Icon(
                    imageVector = DialogIcons.Check,
                    contentDescription = stringResource(resource = Res.string.markdown_editor_confirm),
                )
            }
        },
        windowInsets = WindowInsets(0, 0, 0, 0),
    )
}

@Composable
@Preview(showBackground = true)
private fun MarkdownEditorTopAppBarPreview() {
    DialogTheme {
        MarkdownEditorTopAppBar(
            onBackPress = {},
            onConfirm = {},
            selectedTab = RendererTab.WRITE,
            onSelectedTabChanged = {},
        )
    }
}
