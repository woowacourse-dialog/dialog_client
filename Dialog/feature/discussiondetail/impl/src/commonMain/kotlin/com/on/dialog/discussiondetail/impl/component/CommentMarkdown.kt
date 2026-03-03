package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.component.markdown.DialogMarkdown
import kotlin.math.roundToInt

@Composable
fun CommentMarkdown(
    content: String,
    modifier: Modifier = Modifier,
    maxCollapsedHeight: Dp = 120.dp,
) {
    var isDialogVisible by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    val maxCollapsedPx = with(density) { maxCollapsedHeight.toPx() }.roundToInt()

    SubcomposeLayout(modifier = modifier) { constraints ->
        // 1) 전체 높이 측정
        val fullPlaceable = subcompose("full_measure") {
            DialogMarkdown(content = content)
        }.first().measure(constraints)

        val isOverflow = fullPlaceable.height > maxCollapsedPx

        // 2) 실제 화면에 그릴 컨텐츠(접힘/펼침)
        val contentPlaceable = subcompose("content") {
            CommentMarkdownCard(
                content = content,
                isOverflow = isOverflow,
                maxCollapsedHeight = maxCollapsedHeight,
                onOpen = { isDialogVisible = true },
            )
        }.first().measure(constraints)

        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }

    if (isDialogVisible) {
        CommentMarkdownDialog(
            content = content,
            onDismiss = { isDialogVisible = false },
        )
    }
}

@Composable
private fun CommentMarkdownCard(
    content: String,
    isOverflow: Boolean,
    maxCollapsedHeight: Dp,
    onOpen: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(DialogTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            .fadeOutBottom(
                enabled = isOverflow,
                height = 48.dp,
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            ).then(
                if (isOverflow) {
                    Modifier
                        .heightIn(max = maxCollapsedHeight)
                        .clickable(
                            indication = ripple(),
                            interactionSource = null,
                            onClick = onOpen,
                        )
                } else {
                    Modifier
                },
            ).padding(DialogTheme.spacing.medium),
    ) {
        DialogMarkdown(content = content)
    }
}

@Composable
private fun CommentMarkdownDialog(
    content: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = DialogTheme.spacing.large),
            shape = DialogTheme.shapes.medium,
            tonalElevation = 2.dp,
            shadowElevation = 8.dp,
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = DialogTheme.spacing.large)
                        .padding(top = DialogTheme.spacing.large)
                        .verticalScroll(rememberScrollState()),
                ) {
                    DialogMarkdown(content = content)
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    DialogButton(
                        text = "닫기",
                        onClick = onDismiss,
                        style = DialogButtonStyle.None,
                        modifier = Modifier.align(Alignment.CenterEnd),
                    )
                }
            }
        }
    }
}

@Composable
private fun Modifier.fadeOutBottom(
    enabled: Boolean,
    height: Dp,
    backgroundColor: Color,
): Modifier {
    if (!enabled) return this
    val density = LocalDensity.current

    return this.drawWithContent {
        drawContent()

        val fadeHeightPx = with(density) { height.toPx() }
        val topY = size.height - fadeHeightPx

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Transparent, backgroundColor),
                startY = topY,
                endY = size.height,
            ),
            topLeft = Offset(0f, topY),
        )
    }
}

@ThemePreview
@Composable
private fun CommentMarkdownPreview() {
    DialogTheme {
        Surface {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                CommentMarkdown(
                    content =
                        """
                        실무에서는 ‘완벽한 설계’보다 ‘변경 비용을 낮추는 설계’가 더 중요하다고 느꼈어요. 그래서 모듈 경계랑 책임 분리가 먼저인 듯.
                        """.trimIndent(),
                )

                CommentMarkdown(
                    content =
                        """
                        실무에서는 ‘완벽한 설계’보다 ‘변경 비용을 낮추는 설계’가 더 중요하다고 느꼈어요. 그래서 모듈 경계랑 책임 분리가 먼저인 듯.
                        실무에서는 ‘완벽한 설계’보다 ‘변경 비용을 낮추는 설계’가 더 중요하다고 느꼈어요. 그래서 모듈 경계랑 책임 분리가 먼저인 듯.
                        실무에서는 ‘완벽한 설계’보다 ‘변경 비용을 낮추는 설계’가 더 중요하다고 느꼈어요. 그래서 모듈 경계랑 책임 분리가 먼저인 듯.
                        실무에서는 ‘완벽한 설계’보다 ‘변경 비용을 낮추는 설계’가 더 중요하다고 느꼈어요. 그래서 모듈 경계랑 책임 분리가 먼저인 듯.
                        """.trimIndent(),
                )
            }
        }
    }
}

@ThemePreview
@Composable
private fun CommentMarkdownDialogPreview() {
    DialogTheme {
        CommentMarkdownDialog(
            modifier = Modifier.padding(8.dp),
            content =
                """
                실무에서는 ‘완벽한 설계’보다 ‘변경 비용을 낮추는 설계’가 더 중요하다고 느꼈어요. 그래서 모듈 경계랑 책임 분리가 먼저인 듯.
                """.trimIndent(),
            onDismiss = {},
        )
    }
}
