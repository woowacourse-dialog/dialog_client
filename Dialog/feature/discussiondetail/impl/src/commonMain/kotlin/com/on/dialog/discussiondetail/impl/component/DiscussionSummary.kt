package com.on.dialog.discussiondetail.impl.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.component.DialogCardTone
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.summary_discussion
import dialog.feature.discussiondetail.impl.generated.resources.summary_generating_base
import dialog.feature.discussiondetail.impl.generated.resources.summary_generating_notice
import dialog.feature.discussiondetail.impl.generated.resources.summary_if_finished
import dialog.feature.discussiondetail.impl.generated.resources.summary_only_author
import dialog.feature.discussiondetail.impl.generated.resources.summary_only_once
import dialog.feature.discussiondetail.impl.generated.resources.summary_show_less
import dialog.feature.discussiondetail.impl.generated.resources.summary_show_more
import dialog.feature.discussiondetail.impl.generated.resources.summary_with_ai
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionSummary(
    summary: String?,
    isMyDiscussion: Boolean,
    isGeneratingSummary: Boolean,
    onSummaryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogCard(
        tone = DialogCardTone.SurfaceContainerLow,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small)) {
            IconTextRow(
                iconImage = DialogIcons.AutoAwesome,
                text = stringResource(Res.string.summary_discussion),
                textStyle = DialogTheme.typography.titleMedium,
            )

            if (summary == null) {
                SummaryEmptyContent(
                    isMyDiscussion = isMyDiscussion,
                    isGeneratingSummary = isGeneratingSummary,
                    onSummaryClick = onSummaryClick,
                )
            } else {
                SummaryMarkdownContent(summary = summary)
            }
        }
    }
}

@Composable
private fun SummaryEmptyContent(
    isMyDiscussion: Boolean,
    isGeneratingSummary: Boolean,
    onSummaryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var generatingDotCount by remember { mutableIntStateOf(1) }
    LaunchedEffect(isGeneratingSummary) {
        if (!isGeneratingSummary) {
            generatingDotCount = 1
            return@LaunchedEffect
        }
        while (true) {
            delay(350L)
            generatingDotCount = (generatingDotCount % 3) + 1
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = DialogTheme.spacing.medium),
    ) {
        if (isMyDiscussion) {
            Text(
                text = stringResource(Res.string.summary_if_finished),
                style = DialogTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(
                    if (isGeneratingSummary) {
                        Res.string.summary_generating_notice
                    } else {
                        Res.string.summary_only_once
                    },
                ),
                style = DialogTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = if (isGeneratingSummary) Modifier.fillMaxWidth() else Modifier,
            )
            DialogButton(
                text = if (isGeneratingSummary) {
                    stringResource(Res.string.summary_generating_base) + ".".repeat(generatingDotCount)
                } else {
                    stringResource(Res.string.summary_with_ai)
                },
                onClick = onSummaryClick,
                enabled = !isGeneratingSummary,
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            Text(
                text = stringResource(Res.string.summary_if_finished),
                style = DialogTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = stringResource(Res.string.summary_only_author),
                style = DialogTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun SummaryMarkdownContent(
    summary: String,
    modifier: Modifier = Modifier,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val maxCollapsedHeight = 120.dp
    val collapsedPx = with(LocalDensity.current) { maxCollapsedHeight.roundToPx() }

    // 전체 콘텐츠 높이(px)
    var fullContentHeightPx by remember { mutableIntStateOf(0) }
    val shouldShowToggle = fullContentHeightPx > collapsedPx

    Column(modifier = modifier.fillMaxWidth()) {
        SubcomposeLayout(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium,
                    ),
                ).clipToBounds(),
        ) { constraints ->
            // 1) 전체 높이 측정(세로 무제한)
            val looseConstraints = constraints.copy(maxHeight = Constraints.Infinity)
            val fullPlaceable = subcompose("measure") {
                Markdown(
                    content = summary,
                    colors = markdownColor(),
                    typography = markdownTypography(),
                    modifier = Modifier.fillMaxWidth(),
                )
            }.first().measure(looseConstraints)

            if (fullContentHeightPx != fullPlaceable.height) {
                fullContentHeightPx = fullPlaceable.height
            }

            // 2) 실제 표시용 측정(접힘이면 maxHeight 제한)
            val displayConstraints =
                if (!isExpanded && fullPlaceable.height > collapsedPx) {
                    constraints.copy(maxHeight = collapsedPx)
                } else {
                    constraints
                }

            val displayPlaceable = subcompose("content") {
                Markdown(
                    content = summary,
                    colors = markdownColor(),
                    typography = markdownTypography(),
                    modifier = Modifier.fillMaxWidth(),
                )
            }.first().measure(displayConstraints)

            layout(width = displayPlaceable.width, height = displayPlaceable.height) {
                displayPlaceable.place(0, 0)
            }
        }

        if (shouldShowToggle) {
            DialogButton(
                text = stringResource(
                    if (isExpanded) Res.string.summary_show_less else Res.string.summary_show_more,
                ),
                onClick = { isExpanded = !isExpanded },
                style = DialogButtonStyle.Secondary,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@ThemePreview
@Composable
private fun DiscussionSummaryEmptyMyDiscussionPreview(modifier: Modifier = Modifier) {
    DialogTheme {
        Surface {
            DiscussionSummary(
                summary = null,
                isMyDiscussion = true,
                isGeneratingSummary = false,
                onSummaryClick = {},
                modifier = modifier,
            )
        }
    }
}

@ThemePreview
@Composable
private fun DiscussionSummaryGeneratingMyDiscussionPreview(modifier: Modifier = Modifier) {
    DialogTheme {
        Surface {
            DiscussionSummary(
                summary = null,
                isMyDiscussion = true,
                isGeneratingSummary = true,
                onSummaryClick = {},
                modifier = modifier,
            )
        }
    }
}

@ThemePreview
@Composable
private fun DiscussionSummaryEmptyNotMyDiscussionPreview(modifier: Modifier = Modifier) {
    DialogTheme {
        Surface {
            DiscussionSummary(
                summary = null,
                isMyDiscussion = false,
                isGeneratingSummary = false,
                onSummaryClick = {},
                modifier = modifier,
            )
        }
    }
}

@ThemePreview
@Composable
private fun DiscussionLongSummaryPreview() {
    DialogTheme {
        Surface {
            DiscussionSummary(
                summary =
                    """
                    1. **토론의 핵심 주제**

                    - 이 토론은 Koin의 기능과 역할에 대한 논의

                    2. **참여자별 입장 비교**

                    | 참여자 | 주요 주장 | 근거 요약 |
                    | --- | --- | --- |
                    | 크림 | Koin은 Service Locator이다 | Koin |
                    """.trimIndent(),
                isMyDiscussion = true,
                isGeneratingSummary = false,
                onSummaryClick = {},
            )
        }
    }
}

@ThemePreview
@Composable
private fun DiscussionShortSummaryPreview() {
    DialogTheme {
        Surface {
            DiscussionSummary(
                summary = "이 토론은 Koin의 기능과 역할에 대한 논의",
                isMyDiscussion = true,
                isGeneratingSummary = false,
                onSummaryClick = {},
            )
        }
    }
}
