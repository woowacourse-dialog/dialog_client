package com.on.dialog.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme

/**
 * Dialog 디자인 시스템의 커스텀 텍스트 필드입니다.
 *
 * @param value 텍스트 필드에 표시될 텍스트.
 * @param onValueChange 텍스트가 변경될 때 호출되는 콜백.
 * @param modifier 컴포넌트에 적용할 [Modifier].
 * @param placeholder 입력란이 비어있을 때 표시될 텍스트.
 * @param label 텍스트 필드 위에 표시될 라벨.
 * @param supportingText 텍스트 필드 아래에 표시될 보조 텍스트 (힌트 또는 에러 메시지).
 * @param singleLine `true`이면 한 줄로, `false`이면 여러 줄로 입력됩니다.
 * @param readOnly `true`이면 읽기 전용으로 설정됩니다.
 * @param isError `true`이면 에러 상태로 표시됩니다 (보조 텍스트가 빨간색으로 변경).
 * @param enabled `false`이면 비활성화되어 입력 및 포커스가 불가능합니다.
 * @param keyboardOptions 키보드 타입, IME 액션 등 키보드 관련 설정.
 * @param keyboardActions 키보드 액션(예: 완료, 다음)에 대한 콜백.
 * @param leadingIcon 텍스트 필드 앞에 표시될 아이콘.
 * @param trailingIcon 텍스트 필드 뒤에 표시될 아이콘.
 */
@Composable
fun DialogTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    supportingText: String? = null,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    enabled: Boolean = true,
    cornerBasedShape: CornerBasedShape? = DialogTheme.shapes.small,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    DialogTextFieldLayout(
        modifier = modifier,
        label = label,
        supportingText = supportingText,
        isError = isError,
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            placeholder = { DialogTextFieldPlaceholder(placeholder) },
            modifier = dialogTextFieldModifier(
                singleLine = singleLine,
                cornerBasedShape = cornerBasedShape,
            ),
            readOnly = readOnly,
            enabled = enabled,
            textStyle = dialogTextFieldTextStyle(),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = dialogTextFieldColors(),
        )
    }
}

@Composable
fun DialogTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    supportingText: String? = null,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    enabled: Boolean = true,
    cornerBasedShape: CornerBasedShape? = DialogTheme.shapes.small,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    DialogTextFieldLayout(
        modifier = modifier,
        label = label,
        supportingText = supportingText,
        isError = isError,
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            placeholder = { DialogTextFieldPlaceholder(placeholder) },
            modifier = dialogTextFieldModifier(
                singleLine = singleLine,
                cornerBasedShape = cornerBasedShape,
            ),
            readOnly = readOnly,
            enabled = enabled,
            textStyle = dialogTextFieldTextStyle(),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = dialogTextFieldColors(),
        )
    }
}

@Composable
private fun DialogTextFieldLayout(
    modifier: Modifier,
    label: String?,
    supportingText: String?,
    isError: Boolean,
    textField: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier) {
        label?.let {
            Text(
                text = it,
                color = DialogTheme.colorScheme.primary,
                style = DialogTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(DialogTheme.spacing.small),
            )
        }

        textField()

        supportingText?.let {
            Text(
                text = it,
                color = if (isError) DialogTheme.colorScheme.error else DialogTheme.colorScheme.primary,
                style = DialogTheme.typography.labelSmall,
                modifier = Modifier.padding(DialogTheme.spacing.small),
            )
        }
    }
}

@Composable
private fun dialogTextFieldTextStyle(): TextStyle = DialogTheme.typography.bodyLarge.copy(
    color = DialogTheme.colorScheme.onSurface,
    fontWeight = FontWeight.SemiBold,
)

private fun ColumnScope.dialogTextFieldModifier(
    singleLine: Boolean,
    cornerBasedShape: CornerBasedShape?,
): Modifier = Modifier
    .fillMaxWidth()
    .then(
        if (!singleLine) {
            Modifier.weight(1f, fill = true)
        } else {
            Modifier
        },
    ).then(
        cornerBasedShape?.let { Modifier.clip(it) } ?: Modifier,
    )

@Composable
private fun DialogTextFieldPlaceholder(text: String) {
    if (text.isBlank()) return

    Text(
        text = text,
        color = DialogTheme.colorScheme.onSurfaceVariant,
        style = DialogTheme.typography.bodyLarge,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
private fun dialogTextFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = DialogTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f),
    unfocusedContainerColor = DialogTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f),
    disabledContainerColor = DialogTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f),
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    errorIndicatorColor = Color.Transparent,
    cursorColor = DialogTheme.colorScheme.onSurface,
)

@ThemePreview
@Composable
private fun DialogTextFieldPreviewContent() {
    var text by remember { mutableStateOf("") }

    DialogTheme {
        Surface {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                DialogTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = "닉네임",
                    placeholder = "이름을 입력하세요",
                    supportingText = "10자 이내",
                )
                DialogTextField(
                    value = "다이얼로그 다이얼로그 다이얼로그",
                    onValueChange = { text = it },
                    label = "닉네임",
                    supportingText = "10자 이내",
                    isError = true,
                )
            }
        }
    }
}
