package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogCard
import com.on.dialog.designsystem.component.DialogCardTone
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.summary_discussion
import dialog.feature.discussiondetail.impl.generated.resources.summary_if_finished
import dialog.feature.discussiondetail.impl.generated.resources.summary_only_author
import dialog.feature.discussiondetail.impl.generated.resources.summary_only_once
import dialog.feature.discussiondetail.impl.generated.resources.summary_with_ai
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionSummary(
    summary: String?,
    isMyDiscussion: Boolean,
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
            )

            if (summary == null) {
                SummaryEmptyContent(
                    isMyDiscussion = isMyDiscussion,
                    onSummaryClick = onSummaryClick,
                )
            } else {
                Markdown(
                    content = summary,
                    colors = markdownColor(),
                    typography = markdownTypography(),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun SummaryEmptyContent(
    isMyDiscussion: Boolean,
    onSummaryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (isMyDiscussion) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = DialogTheme.spacing.medium),
        ) {
            Text(
                text = stringResource(Res.string.summary_if_finished),
                style = DialogTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = stringResource(Res.string.summary_only_once),
                style = DialogTheme.typography.bodyMedium,
            )
            DialogButton(
                text = stringResource(Res.string.summary_with_ai),
                onClick = onSummaryClick,
            )
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium),
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = DialogTheme.spacing.medium),
        ) {
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

@Preview
@Composable
private fun DiscussionSummaryEmptyMyDiscussionPreview(modifier: Modifier = Modifier) {
    DialogTheme {
        DiscussionSummary(
            summary = null,
            isMyDiscussion = true,
            onSummaryClick = {},
            modifier = modifier,
        )
    }
}

@Preview
@Composable
private fun DiscussionSummaryEmptyNotMyDiscussionPreview(modifier: Modifier = Modifier) {
    DialogTheme {
        DiscussionSummary(
            summary = null,
            isMyDiscussion = false,
            onSummaryClick = {},
            modifier = modifier,
        )
    }
}

@Preview
@Composable
private fun DiscussionSummaryPreview(modifier: Modifier = Modifier) {
    DialogTheme {
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
            onSummaryClick = {},
        )
    }
}
