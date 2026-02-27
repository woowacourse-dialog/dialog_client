package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogHorizontalDivider
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.model.discussion.content.DiscussionType
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.participate_button
import dialog.feature.discussiondetail.impl.generated.resources.participate_done_button
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionDetailContent(
    discussionType: DiscussionType,
    content: String,
    summary: String?,
    isMyDiscussion: Boolean,
    isGeneratingSummary: Boolean,
    isParticipating: Boolean,
    onSummaryClick: () -> Unit,
    onParticipateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.large),
        modifier = modifier.padding(top = DialogTheme.spacing.medium),
    ) {
        Markdown(
            content = content,
            colors = markdownColor(),
            typography = markdownTypography(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DialogTheme.spacing.large),
        )
        DialogHorizontalDivider()
        Box(
            modifier = Modifier
                .padding(horizontal = DialogTheme.spacing.large)
                .padding(bottom = DialogTheme.spacing.large),
        ) {
            when (discussionType) {
                DiscussionType.ONLINE -> {
                    DiscussionSummary(
                        summary = summary,
                        isMyDiscussion = isMyDiscussion,
                        isGeneratingSummary = isGeneratingSummary,
                        onSummaryClick = onSummaryClick,
                    )
                }

                DiscussionType.OFFLINE -> {
                    ParticipateButton(
                        isParticipating = isParticipating,
                        onParticipateClick = onParticipateClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun ParticipateButton(
    isParticipating: Boolean,
    onParticipateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogButton(
        text = stringResource(if (isParticipating) Res.string.participate_done_button else Res.string.participate_button),
        onClick = onParticipateClick,
        enabled = !isParticipating,
        modifier = modifier.fillMaxWidth(),
    ) {
        Icon(imageVector = DialogIcons.Group, contentDescription = null)
    }
}

@ThemePreview
@Composable
private fun OnlineDiscussionDetailContentPreview() {
    DialogTheme {
        Surface {
            DiscussionDetailContent(
                discussionType = DiscussionType.ONLINE,
                content = "다이얼로그가 무슨 뜻인지 궁금합니다. ".repeat(15),
                summary = "요약된 내용",
                isMyDiscussion = true,
                isGeneratingSummary = false,
                isParticipating = false,
                onSummaryClick = {},
                onParticipateClick = {},
            )
        }
    }
}

@ThemePreview
@Composable
private fun OfflineDiscussionDetailContentPreview() {
    DialogTheme {
        Surface {
            DiscussionDetailContent(
                discussionType = DiscussionType.OFFLINE,
                content = "다이얼로그가 무슨 뜻인지 궁금합니다. ".repeat(15),
                summary = "요약된 내용",
                isMyDiscussion = true,
                isGeneratingSummary = false,
                isParticipating = false,
                onSummaryClick = {},
                onParticipateClick = {},
            )
        }
    }
}
