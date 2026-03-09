package com.on.dialog.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.theme.DialogTheme

/**
 * Dialog 디자인 시스템의 커스텀 토글 컴포넌트입니다.
 *
 * @param checked 토글의 현재 상태 (true: 활성화, false: 비활성화).
 * @param onCheckedChange 토글 상태가 변경될 때 호출되는 콜백.
 * @param label 토글 왼쪽에 표시될 라벨 텍스트.
 * @param modifier 컴포넌트에 적용할 [Modifier].
 * @param supportingText 라벨 아래에 표시될 보조 텍스트.
 * @param enabled `false`이면 비활성화되어 상호작용이 불가능합니다.
 */
@Composable
fun DialogToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = DialogTheme.spacing.large),
        ) {
            Text(
                text = label,
                color = if (enabled) {
                    DialogTheme.colorScheme.onSurface
                } else {
                    DialogTheme.colorScheme.onSurfaceVariant
                },
                style = DialogTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
            )
            supportingText?.let {
                Text(
                    text = it,
                    color = if (enabled) {
                        DialogTheme.colorScheme.onSurfaceVariant
                    } else {
                        DialogTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    },
                    style = DialogTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = DialogTheme.spacing.extraSmall),
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = DialogTheme.colorScheme.onPrimary,
                checkedTrackColor = DialogTheme.colorScheme.primary,
                checkedBorderColor = Color.Transparent,
                uncheckedThumbColor = DialogTheme.colorScheme.onPrimaryContainer,
                uncheckedTrackColor = DialogTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f),
                uncheckedBorderColor = Color.Transparent,
                disabledCheckedThumbColor = DialogTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                disabledCheckedTrackColor = DialogTheme.colorScheme.primary.copy(alpha = 0.3f),
                disabledUncheckedThumbColor = DialogTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f),
                disabledUncheckedTrackColor = DialogTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.15f),
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogTogglePreviewLight() {
    DialogTheme {
        DialogTogglePreviewContent()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF131315)
@Composable
private fun DialogTogglePreviewDark() {
    DialogTheme(darkTheme = true) {
        DialogTogglePreviewContent()
    }
}

@Composable
private fun DialogTogglePreviewContent() {
    var checked1 by remember { mutableStateOf(true) }
    var checked2 by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(DialogTheme.spacing.large),
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.large),
    ) {
        DialogToggle(
            checked = checked1,
            onCheckedChange = { checked1 = it },
            label = "만나서 토론하기",
        )
        DialogToggle(
            checked = checked2,
            onCheckedChange = { checked2 = it },
            label = "알림 받기",
            supportingText = "새로운 토론 주제를 알려드립니다",
        )
        DialogToggle(
            checked = true,
            onCheckedChange = {},
            label = "비활성화 상태",
            supportingText = "변경할 수 없습니다",
            enabled = false,
        )
    }
}
