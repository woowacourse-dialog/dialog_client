package com.on.dialog.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.designsystem.theme.Gray200
import org.jetbrains.compose.ui.tooling.preview.Preview

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
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        label?.let {
            Text(
                text = label,
                color = DialogTheme.colorScheme.primary,
                style = DialogTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(DialogTheme.spacing.extraSmall),
            )
        }
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    style = DialogTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(DialogTheme.shapes.small)
                .background(Gray200),
            readOnly = readOnly,
            enabled = enabled,
            textStyle = DialogTheme.typography.bodyLarge.copy(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
            ),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Gray200,
                unfocusedContainerColor = Gray200,
                disabledContainerColor = Gray200,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
            ),
        )
        supportingText?.let {
            Spacer(Modifier.height(DialogTheme.spacing.extraSmall))
            Text(
                text = it,
                color = if (isError) Color.Red else DialogTheme.colorScheme.primary,
                style = DialogTheme.typography.labelSmall,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogTextFieldPreviewLight() {
    DialogTheme {
        DialogTextFieldPreviewContent()
    }
}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
private fun DialogTextFieldPreviewDark() {
    DialogTheme(darkTheme = true) {
        DialogTextFieldPreviewContent()
    }
}

@Composable
private fun DialogTextFieldPreviewContent() {
    var text by remember { mutableStateOf("") }

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
