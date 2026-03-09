@file:OptIn(ExperimentalMaterial3Api::class)

package com.on.dialog.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TimePickerDialogDefaults
import androidx.compose.material3.TimePickerDisplayMode
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.core.common.extension.formatToString
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.core.designsystem.generated.resources.Res
import dialog.core.designsystem.generated.resources.dialog_time_picker_cancel
import dialog.core.designsystem.generated.resources.dialog_time_picker_confirm
import dialog.core.designsystem.generated.resources.dialog_time_picker_content_description
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource

/**
 * Dialog 디자인 시스템의 시간 선택 컴포넌트.
 *
 * 클릭 시 시간을 선택할 수 있는 TimePickerDialog를 표시하는 텍스트 필드 형태의 컴포넌트입니다.
 *
 * @param selectedTime 현재 선택된 시간. `null`이면 플레이스홀더가 표시됩니다.
 * @param onTimeSelected 시간이 선택되었을 때 호출되는 콜백. 선택된 [LocalTime]을 전달합니다.
 * @param modifier 컴포넌트에 적용할 [Modifier].
 * @param label 텍스트 필드 위에 표시될 라벨.
 * @param placeholder 선택된 시간이 없을 때 텍스트 필드 내부에 표시될 텍스트.
 * @param supportingText 텍스트 필드 아래에 표시될 보조 텍스트.
 * @param timeFormatter 시간을 문자열로 변환하는 함수. 기본값은 `HH:mm` 형식.
 * @param is24Hour `true`이면 24시간 형식, `false`이면 AM/PM 형식으로 표시됩니다.
 * @param enabled 컴포넌트 활성화 여부. `false`일 경우 비활성화됩니다.
 * @param isError `true`이면 에러 상태로 표시됩니다.
 */
@Composable
fun DialogTimePicker(
    selectedTime: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = "",
    supportingText: String? = null,
    timeFormatter: (LocalTime) -> String = { it.formatToString() },
    is24Hour: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false,
) {
    var showDialog by remember { mutableStateOf(false) }

    DialogPickerTextField(
        value = selectedTime?.let { timeFormatter(it) } ?: "",
        onClick = { showDialog = true },
        enabled = enabled,
        label = label,
        placeholder = placeholder,
        supportingText = supportingText,
        isError = isError,
        trailingIcon = { DialogTimePickerIcon() },
        modifier = modifier,
    )

    if (showDialog) {
        DialogTimePickerDialog(
            selectedTime = selectedTime,
            is24Hour = is24Hour,
            onDismiss = { showDialog = false },
            onConfirm = { hour, minute ->
                onTimeSelected(LocalTime(hour, minute))
                showDialog = false
            },
        )
    }
}

@Composable
private fun DialogTimePickerDialog(
    selectedTime: LocalTime?,
    is24Hour: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (hour: Int, minute: Int) -> Unit,
) {
    val timePickerState = rememberTimePickerState(
        initialHour = selectedTime?.hour ?: 0,
        initialMinute = selectedTime?.minute ?: 0,
        is24Hour = is24Hour,
    )
    var displayMode by remember { mutableStateOf(TimePickerDisplayMode.Picker) }

    val timePickerColors = TimePickerDefaults.colors(
        clockDialColor = DialogTheme.colorScheme.surfaceContainerHighest,
        clockDialSelectedContentColor = DialogTheme.colorScheme.onPrimary,
        clockDialUnselectedContentColor = DialogTheme.colorScheme.onSurface,
        selectorColor = DialogTheme.colorScheme.primary,
        containerColor = DialogTheme.colorScheme.surfaceContainerHigh,
        periodSelectorBorderColor = DialogTheme.colorScheme.outline,
        periodSelectorSelectedContainerColor = DialogTheme.colorScheme.primaryContainer,
        periodSelectorUnselectedContainerColor = DialogTheme.colorScheme.surfaceContainerHigh,
        periodSelectorSelectedContentColor = DialogTheme.colorScheme.onPrimaryContainer,
        periodSelectorUnselectedContentColor = DialogTheme.colorScheme.onSurfaceVariant,
        timeSelectorSelectedContainerColor = DialogTheme.colorScheme.primaryContainer,
        timeSelectorUnselectedContainerColor = DialogTheme.colorScheme.surfaceContainerHighest,
        timeSelectorSelectedContentColor = DialogTheme.colorScheme.onPrimaryContainer,
        timeSelectorUnselectedContentColor = DialogTheme.colorScheme.onSurface,
    )

    TimePickerDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.padding(horizontal = DialogTheme.spacing.medium),
        title = { TimePickerDialogDefaults.Title(displayMode = displayMode) },
        modeToggleButton = {
            TimePickerDialogDefaults.DisplayModeToggle(
                displayMode = displayMode,
                onDisplayModeChange = {
                    displayMode = when (displayMode) {
                        TimePickerDisplayMode.Picker -> TimePickerDisplayMode.Input
                        else -> TimePickerDisplayMode.Picker
                    }
                },
            )
        },
        confirmButton = {
            DialogButton(
                text = stringResource(Res.string.dialog_time_picker_confirm),
                onClick = { onConfirm(timePickerState.hour, timePickerState.minute) },
                style = DialogButtonStyle.None,
            )
        },
        dismissButton = {
            DialogButton(
                text = stringResource(Res.string.dialog_time_picker_cancel),
                onClick = onDismiss,
                style = DialogButtonStyle.None,
            )
        },
        containerColor = DialogTheme.colorScheme.surfaceContainerHigh,
    ) {
        if (displayMode == TimePickerDisplayMode.Picker) {
            TimePicker(
                state = timePickerState,
                colors = timePickerColors,
            )
        } else {
            TimeInput(
                state = timePickerState,
                colors = timePickerColors,
            )
        }
    }
}

@Composable
private fun DialogTimePickerIcon() {
    Icon(
        imageVector = Icons.Default.Schedule,
        contentDescription = stringResource(Res.string.dialog_time_picker_content_description),
        tint = DialogTheme.colorScheme.onSurfaceVariant,
    )
}

@Preview(showBackground = true)
@Composable
private fun DialogTimePickerPreviewLight() {
    DialogTheme {
        DialogTimePickerPreviewContent()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF131315)
@Composable
private fun DialogTimePickerPreviewDark() {
    DialogTheme(darkTheme = true) {
        DialogTimePickerPreviewContent()
    }
}

@Composable
private fun DialogTimePickerPreviewContent() {
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }

    DialogTimePickerDialog(
        selectedTime = selectedTime,
        is24Hour = true,
        onDismiss = { },
        onConfirm = {_,_ ->},
    )
}
