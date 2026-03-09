package com.on.dialog.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.core.designsystem.generated.resources.Res
import dialog.core.designsystem.generated.resources.dialog_time_picker_content_description
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DialogPickerTextField(
    value: String,
    onClick: () -> Unit,
    enabled: Boolean,
    label: String?,
    placeholder: String,
    supportingText: String?,
    isError: Boolean,
    trailingIcon: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        DialogTextField(
            label = label,
            readOnly = true,
            supportingText = supportingText,
            value = value,
            placeholder = placeholder,
            onValueChange = {},
            trailingIcon = trailingIcon,
            isError = isError,
            enabled = enabled,
        )

        if (enabled) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onClick,
                    ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogPickerTextFieldPreviewLight() {
    DialogTheme {
        DialogPickerTextFieldPreviewContent()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF131315)
@Composable
private fun DialogPickerTextFieldPreviewDark() {
    DialogTheme(darkTheme = true) {
        DialogPickerTextFieldPreviewContent()
    }
}

@Composable
private fun DialogPickerTextFieldPreviewContent() {
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(DialogTheme.spacing.large),
    ) {
        DialogPickerTextField(
            value = "",
            onClick = { selectedTime = LocalTime(14, 30) },
            enabled = true,
            label = "시간",
            placeholder = "시간을 선택해주세요",
            supportingText = "토론 시작 시간",
            isError = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = stringResource(Res.string.dialog_time_picker_content_description),
                    tint = DialogTheme.colorScheme.onSurfaceVariant,
                )
            },
        )

        DialogPickerTextField(
            value = "",
            onClick = { },
            enabled = true,
            label = "닐짜",
            placeholder = "닐찌를 선택해주세요",
            supportingText = "올바르지 않은 날짜 입니다.",
            isError = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = stringResource(Res.string.dialog_time_picker_content_description),
                    tint = DialogTheme.colorScheme.onSurfaceVariant,
                )
            },
        )

        DialogPickerTextField(
            value = "",
            onClick = { },
            enabled = true,
            label = "닐짜",
            placeholder = "닐찌를 선택해주세요",
            supportingText = null,
            isError = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = stringResource(Res.string.dialog_time_picker_content_description),
                    tint = DialogTheme.colorScheme.onSurfaceVariant,
                )
            },
        )
    }
}
