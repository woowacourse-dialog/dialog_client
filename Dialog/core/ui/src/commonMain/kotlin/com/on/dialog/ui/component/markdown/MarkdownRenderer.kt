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

enum class RendererTab {
    WRITE,
    PREVIEW,
}

@Composable
fun MarkdownRenderer(
    text: String,
    onClickContent: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        var selectedTab: RendererTab by rememberSaveable { mutableStateOf(RendererTab.WRITE) }

        SecondaryTabRow(
            selectedTabIndex = selectedTab.ordinal,
            indicator = {},
        ) {
            AnimatedTab(
                title = stringResource(Res.string.markdown_renderer_write),
                selected = selectedTab == RendererTab.WRITE,
                onClick = { selectedTab = RendererTab.WRITE },
                modifier = Modifier.padding(
                    horizontal = DialogTheme.spacing.extraSmall,
                    vertical = DialogTheme.spacing.small,
                ),
            )

            AnimatedTab(
                title = stringResource(Res.string.markdown_renderer_preview),
                selected = selectedTab == RendererTab.PREVIEW,
                onClick = { selectedTab = RendererTab.PREVIEW },
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
                    enabled = selectedTab == RendererTab.WRITE,
                    onClick = { onClickContent() },
                ),
        ) {
            when (selectedTab) {
                RendererTab.WRITE -> {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                RendererTab.PREVIEW -> {
                    Markdown(
                        content = text,
                        colors = markdownColor(),
                        typography = markdownTypography(),
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
