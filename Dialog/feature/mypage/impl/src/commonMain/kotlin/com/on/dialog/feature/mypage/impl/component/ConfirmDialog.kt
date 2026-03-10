package com.on.dialog.feature.mypage.impl.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogButton
import com.on.dialog.designsystem.component.DialogButtonStyle
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.feature.mypage.impl.generated.resources.Res
import dialog.feature.mypage.impl.generated.resources.cancel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ConfirmDialog(
    title: String,
    message: String,
    confirmText: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    confirmButtonStyle: DialogButtonStyle = DialogButtonStyle.Primary,
) {
    BasicAlertDialog(
        modifier = modifier
            .background(color = DialogTheme.colorScheme.surface, shape = DialogTheme.shapes.medium)
            .padding(all = 20.dp),
        onDismissRequest = onDismissRequest,
    ) {
        Column {
            Text(
                text = title,
                style = DialogTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(DialogTheme.spacing.small))
            Text(
                text = message,
                style = DialogTheme.typography.bodyMedium,
                color = DialogTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )
            Spacer(modifier = Modifier.height(DialogTheme.spacing.large))
            Row(horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.medium)) {
                DialogButton(
                    text = stringResource(Res.string.cancel),
                    style = DialogButtonStyle.Secondary,
                    onClick = onDismissRequest,
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
}

@ThemePreview
@Composable
private fun LogoutConfirmDialogPreview() {
    DialogTheme {
        Surface {
            ConfirmDialog(
                title = "로그아웃",
                message = "정말 로그아웃 하시겠어요?",
                confirmText = "로그아웃",
                onDismissRequest = {},
                onConfirm = {},
            )
        }
    }
}

@ThemePreview
@Composable
private fun DeleteAccountConfirmDialogPreview() {
    DialogTheme {
        Surface {
            ConfirmDialog(
                title = "회원 탈퇴",
                message = "회원 탈퇴 시 모든 정보가 삭제되며\n복구가 불가능해요.",
                confirmText = "탈퇴하기",
                onDismissRequest = {},
                onConfirm = {},
                confirmButtonStyle = DialogButtonStyle.Error,
            )
        }
    }
}
