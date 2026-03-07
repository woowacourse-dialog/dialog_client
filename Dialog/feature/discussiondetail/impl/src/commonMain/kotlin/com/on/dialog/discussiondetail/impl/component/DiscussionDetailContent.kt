package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogHorizontalDivider
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.ui.component.markdown.DialogMarkdown
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.participate_button
import dialog.feature.discussiondetail.impl.generated.resources.participate_done_button
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DiscussionDetailContent(
    content: String,
    summary: String?,
    isMyDiscussion: Boolean,
    isShowParticipateButton: Boolean,
    isShowSummary: Boolean,
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
        DialogMarkdown(
            content = content,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DialogTheme.spacing.large),
        )

        Column(modifier = Modifier.padding(horizontal = DialogTheme.spacing.large)) {
            val includeSpacing = isShowSummary || isShowParticipateButton

            if (includeSpacing) {
                DialogHorizontalDivider()
                Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
            }

            if (isShowSummary) {
                DiscussionSummary(
                    summary = summary,
                    isMyDiscussion = isMyDiscussion,
                    isGeneratingSummary = isGeneratingSummary,
                    onSummaryClick = onSummaryClick,
                )
            }

            if (isShowParticipateButton) {
                ParticipateButton(
                    isParticipating = isParticipating,
                    onParticipateClick = onParticipateClick,
                )
            }

            if (includeSpacing) Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))
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
                content = "다이얼로그가 무슨 뜻인지 궁금합니다. ".repeat(15),
                summary = "요약된 내용",
                isMyDiscussion = true,
                isShowParticipateButton = false,
                isShowSummary = true,
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
                content = "다이얼로그가 무슨 뜻인지 궁금합니다. ".repeat(15),
                summary = "요약된 내용",
                isMyDiscussion = true,
                isShowParticipateButton = true,
                isShowSummary = false,
                isGeneratingSummary = false,
                isParticipating = false,
                onSummaryClick = {},
                onParticipateClick = {},
            )
        }
    }
}
