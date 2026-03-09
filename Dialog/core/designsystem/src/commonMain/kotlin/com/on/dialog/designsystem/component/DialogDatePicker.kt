@file:OptIn(ExperimentalMaterial3Api::class)

package com.on.dialog.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.on.dialog.core.common.extension.formatToString
import com.on.dialog.designsystem.theme.DialogTheme
import dialog.core.designsystem.generated.resources.Res
import dialog.core.designsystem.generated.resources.dialog_date_picker_cancel
import dialog.core.designsystem.generated.resources.dialog_date_picker_confirm
import dialog.core.designsystem.generated.resources.dialog_date_picker_content_description
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Dialog 디자인 시스템의 날짜 선택 컴포넌트.
 *
 * 클릭 시 날짜를 선택할 수 있는 DatePickerDialog를 표시하는 텍스트 필드 형태의 컴포넌트입니다.
 *
 * @param selectedDate 현재 선택된 날짜. `null`이면 플레이스홀더가 표시됩니다.
 * @param onDateSelected 날짜가 선택되었을 때 호출되는 콜백. 선택된 [LocalDate]를 전달합니다.
 * @param modifier 컴포넌트에 적용할 [Modifier].
 * @param label 텍스트 필드 위에 표시될 라벨.
 * @param placeholder 선택된 날짜가 없을 때 텍스트 필드 내부에 표시될 텍스트.
 * @param supportingText 텍스트 필드 아래에 표시될 보조 텍스트.
 * @param dateFormatter 날짜를 문자열로 변환하는 함수. 기본값은 `yyyy.MM.dd` 형식.
 * @param enabled 컴포넌트 활성화 여부. `false`일 경우 비활성화됩니다.
 * @param isError `true`이면 에러 상태로 표시됩니다.
 */
@OptIn(ExperimentalTime::class)
@Composable
fun DialogDatePicker(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = "",
    supportingText: String? = null,
    dateFormatter: (LocalDate) -> String = { it.formatToString("yyyy.MM.dd") },
    enabled: Boolean = true,
    isError: Boolean = false,
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        DialogTextField(
            label = label,
            readOnly = true,
            supportingText = supportingText,
            value = selectedDate?.let { dateFormatter(it) } ?: "",
            placeholder = placeholder,
            onValueChange = {},
            trailingIcon = { DialogDatePickerIcon() },
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
                        onClick = { showDialog = true },
                    ),
            )
        }
    }

    if (showDialog) {
        DialogDatePickerDialog(
            selectedDate = selectedDate,
            onDismiss = { showDialog = false },
            onConfirm = { millis ->
                val date = Instant.fromEpochMilliseconds(millis)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date
                onDateSelected(date)
                showDialog = false
            },
        )
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun DialogDatePickerDialog(
    selectedDate: LocalDate?,
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
            ?.atStartOfDayIn(TimeZone.currentSystemDefault())
            ?.toEpochMilliseconds(),
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            DialogButton(
                text = stringResource(Res.string.dialog_date_picker_confirm),
                onClick = {
                    datePickerState.selectedDateMillis?.let { onConfirm(it) }
                },
                style = DialogButtonStyle.None,
            )
        },
        dismissButton = {
            DialogButton(
                text = stringResource(Res.string.dialog_date_picker_cancel),
                onClick = onDismiss,
                style = DialogButtonStyle.None,
            )
        },
        colors = DatePickerDefaults.colors(
            containerColor = DialogTheme.colorScheme.surfaceContainerHigh,
        ),
        modifier = Modifier
            .imePadding()
            .fillMaxHeight(),
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = DialogTheme.colorScheme.surfaceContainerHigh,
                titleContentColor = DialogTheme.colorScheme.onSurface,
                headlineContentColor = DialogTheme.colorScheme.primary,
                weekdayContentColor = DialogTheme.colorScheme.onSurfaceVariant,
                subheadContentColor = DialogTheme.colorScheme.onSurfaceVariant,
                navigationContentColor = DialogTheme.colorScheme.onSurface,
                yearContentColor = DialogTheme.colorScheme.onSurface,
                currentYearContentColor = DialogTheme.colorScheme.primary,
                selectedYearContentColor = DialogTheme.colorScheme.onPrimary,
                selectedYearContainerColor = DialogTheme.colorScheme.primary,
                dayContentColor = DialogTheme.colorScheme.onSurface,
                selectedDayContentColor = DialogTheme.colorScheme.onPrimary,
                selectedDayContainerColor = DialogTheme.colorScheme.primary,
                todayContentColor = DialogTheme.colorScheme.primary,
                todayDateBorderColor = DialogTheme.colorScheme.primary,
                dividerColor = Color.Transparent,
            ),
        )
    }
}

@Composable
private fun DialogDatePickerIcon() {
    Icon(
        imageVector = Icons.Default.DateRange,
        contentDescription = stringResource(Res.string.dialog_date_picker_content_description),
        tint = DialogTheme.colorScheme.onSurfaceVariant,
    )
}

@Preview(showBackground = true)
@Composable
private fun DialogDatePickerPreviewLight() {
    DialogTheme {
        DialogDatePickerPreviewContent()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF131315)
@Composable
private fun DialogDatePickerPreviewDark() {
    DialogTheme(darkTheme = true) {
        DialogDatePickerPreviewContent()
    }
}

@Composable
private fun DialogDatePickerPreviewContent() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        DialogDatePicker(
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it },
            label = "날짜",
            placeholder = "날짜를 선택해주세요",
            supportingText = "토론 시작일",
        )
        DialogDatePicker(
            selectedDate = LocalDate(2025, 3, 8),
            onDateSelected = {},
            label = "날짜",
            isError = true,
            supportingText = "올바른 날짜를 선택해주세요",
        )
        DialogDatePicker(
            selectedDate = null,
            onDateSelected = {},
            label = "날짜",
            placeholder = "날짜를 선택해주세요",
            enabled = false,
        )
    }
}