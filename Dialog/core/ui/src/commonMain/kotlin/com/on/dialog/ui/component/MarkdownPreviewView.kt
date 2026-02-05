package com.on.dialog.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.on.dialog.designsystem.component.AnimatedTab
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.extensions.noRippleClickable
import dialog.core.ui.generated.resources.Res
import dialog.core.ui.generated.resources.markdown_preview_preview
import dialog.core.ui.generated.resources.markdown_preview_write
import org.jetbrains.compose.resources.stringResource

@Composable
fun MarkdownPreviewView(
    text: String,
    onClickContent: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        var selectedTabIndex: Int by remember { mutableStateOf(0) }

        SecondaryTabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = {},
        ) {
            AnimatedTab(
                title = stringResource(Res.string.markdown_preview_write),
                selected = selectedTabIndex == 0,
                onClick = {
                    selectedTabIndex = 0
                },
                modifier = Modifier.padding(
                    horizontal = DialogTheme.spacing.extraSmall,
                    vertical = DialogTheme.spacing.small,
                )
            )

            AnimatedTab(
                title = stringResource(Res.string.markdown_preview_preview),
                selected = selectedTabIndex == 1,
                onClick = {
                    selectedTabIndex = 1
                },
                modifier = Modifier.padding(
                    horizontal = DialogTheme.spacing.extraSmall,
                    vertical = DialogTheme.spacing.small,
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = DialogTheme.shapes.small
                )
                .padding(DialogTheme.spacing.large)
                .noRippleClickable(
                    enabled = selectedTabIndex == 0,
                    onClick = {
                        onClickContent()
                    }
                )
        ) {
            when (selectedTabIndex == 1) {
                true -> {
                    Markdown(
                        content = text,
                        colors = markdownColor(),
                        typography = markdownTypography()
                    )
                }

                false -> {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarkdownPreviewViewPreview() {
    DialogTheme {
        MarkdownPreviewView(
            text = "#### Hello World\n\nThis is a **bold** statement.",
            modifier = Modifier.size(500.dp, 300.dp),
            onClickContent = {},
        )
    }
}
