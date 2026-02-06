package com.on.dialog.ui.component.markdown

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
import androidx.compose.runtime.saveable.rememberSaveable
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
import dialog.core.ui.generated.resources.markdown_renderer_preview
import dialog.core.ui.generated.resources.markdown_renderer_write
import org.jetbrains.compose.resources.stringResource

@Composable
fun MarkdownRenderer(
    text: String,
    onClickContent: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        var selectedTabIndex: Int by rememberSaveable { mutableStateOf(0) }

        SecondaryTabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = {},
        ) {
            AnimatedTab(
                title = stringResource(Res.string.markdown_renderer_write),
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                modifier = Modifier.padding(
                    horizontal = DialogTheme.spacing.extraSmall,
                    vertical = DialogTheme.spacing.small,
                ),
            )

            AnimatedTab(
                title = stringResource(Res.string.markdown_renderer_preview),
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                modifier = Modifier.padding(
                    horizontal = DialogTheme.spacing.extraSmall,
                    vertical = DialogTheme.spacing.small,
                ),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = DialogTheme.shapes.small,
                ).padding(DialogTheme.spacing.large)
                .noRippleClickable(
                    enabled = selectedTabIndex == 0,
                    onClick = { onClickContent() },
                ),
        ) {
            when (selectedTabIndex == 1) {
                true -> {
                    Markdown(
                        content = text,
                        colors = markdownColor(),
                        typography = markdownTypography(),
                    )
                }

                false -> {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MarkdownRendererPreview() {
    DialogTheme {
        MarkdownRenderer(
            text = "#### Hello World\n\nThis is a **bold** statement.",
            modifier = Modifier.size(500.dp, 300.dp),
            onClickContent = {},
        )
    }
}
