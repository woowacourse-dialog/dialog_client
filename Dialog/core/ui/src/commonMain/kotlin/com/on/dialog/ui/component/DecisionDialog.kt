package com.on.dialog.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.theme.DialogTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecisionDialog(
    contentText: String,
    confirmText: String,
    onConfirm: () -> Unit,
    dismissText: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    confirmButtonStyle: DialogButtonStyle = DialogButtonStyle.Primary,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
            .background(color = DialogTheme.colorScheme.surface, shape = DialogTheme.shapes.medium)
            .padding(horizontal = DialogTheme.spacing.large)
            .padding(top = DialogTheme.spacing.large, bottom = DialogTheme.spacing.medium),
    ) {
        DecisionContent(
            contentText = contentText,
            confirmText = confirmText,
            onConfirm = onConfirm,
            confirmButtonStyle = confirmButtonStyle,
            dismissText = dismissText,
            onDismiss = onDismiss,
            modifier = modifier,
        )
    }
}

@Composable
private fun DecisionContent(
    contentText: String,
    confirmText: String,
    onConfirm: () -> Unit,
    confirmButtonStyle: DialogButtonStyle,
    dismissText: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = contentText,
            style = DialogTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.padding(all = DialogTheme.spacing.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = DialogTheme.spacing.small),
        ) {
            DialogButton(
                text = dismissText,
                onClick = onDismiss,
                style = DialogButtonStyle.Secondary,
                modifier = Modifier.weight(1f),
            )
            DialogButton(
                text = confirmText,
                onClick = onConfirm,
                style = confirmButtonStyle,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DecisionDialogPreviewContentLight() {
    DialogTheme {
        DecisionDialogPreviewContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun DecisionDialogPreviewContentDark() {
    DialogTheme(darkTheme = true) {
        DecisionDialogPreviewContent()
    }
}

@Composable
private fun DecisionDialogPreviewContent() {
    Surface {
        DecisionDialog(
            contentText = "뒤로 가시면 변경사항이 저장되지 않습니다.\n정말 나가시겠습니까?",
            confirmText = "뒤로가기",
            onConfirm = {},
            dismissText = "취소",
            onDismiss = {},
        )
    }
}
