package com.on.dialog.discussiondetail.impl.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.discussiondetail.impl.model.ReportReasonUiModel
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.action_cancel
import dialog.feature.discussiondetail.impl.generated.resources.action_report
import dialog.feature.discussiondetail.impl.generated.resources.discussion_report_confirm
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DiscussionReportReasonDialog(
    onDismiss: () -> Unit,
    onConfirm: (ReportReasonUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedReason by rememberSaveable { mutableStateOf(ReportReasonUiModel.SPAM) }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
            .background(color = DialogTheme.colorScheme.surface, shape = DialogTheme.shapes.medium)
            .padding(horizontal = DialogTheme.spacing.large)
            .padding(top = DialogTheme.spacing.large, bottom = DialogTheme.spacing.medium),
    ) {
        Column {
            Text(
                text = stringResource(Res.string.discussion_report_confirm),
                style = DialogTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.height(DialogTheme.spacing.small))

            ReportReasonUiModel.entries.forEach { reason ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedReason = reason },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = selectedReason == reason,
                        onClick = { selectedReason = reason },
                    )
                    Text(
                        text = reason.title,
                        style = DialogTheme.typography.bodyMedium,
                    )
                }
            }

            Spacer(modifier = Modifier.height(DialogTheme.spacing.medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small),
            ) {
                DialogButton(
                    text = stringResource(Res.string.action_cancel),
                    onClick = onDismiss,
                    style = DialogButtonStyle.Secondary,
                    modifier = Modifier.weight(1f),
                )
                DialogButton(
                    text = stringResource(Res.string.action_report),
                    onClick = { onConfirm(selectedReason) },
                    modifier = Modifier.weight(1f),
                    style = DialogButtonStyle.Error,
                )
            }
        }
    }
}

@ThemePreview
@Composable
private fun DiscussionReportReasonDialogPreview() {
    DialogTheme {
        Surface {
            DiscussionReportReasonDialog(
                onDismiss = {},
                onConfirm = {},
            )
        }
    }
}
