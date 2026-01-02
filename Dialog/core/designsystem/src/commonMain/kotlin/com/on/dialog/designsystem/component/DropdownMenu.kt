@file:OptIn(ExperimentalMaterial3Api::class)

package com.on.dialog.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.on.dialog.designsystem.theme.DialogTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Dialog 디자인 시스템의 드롭다운 메뉴 컴포넌트.
 *
 * 클릭 시 선택 가능한 옵션 목록을 보여주는 텍스트 필드 형태의 메뉴입니다.
 *
 * @param options 드롭다운 메뉴에 표시될 문자열 목록.
 * @param onSelectedIndexChange 옵션이 선택되었을 때 호출되는 콜백. 선택된 아이템의 인덱스를 전달합니다.
 * @param modifier 컴포넌트에 적용할 [Modifier].
 * @param selectedIndex 현재 선택된 아이템의 인덱스. `null`이면 플레이스홀더가 표시됩니다.
 * @param label 텍스트 필드 위에 표시될 라벨.
 * @param placeholder 선택된 항목이 없을 때 텍스트 필드 내부에 표시될 텍스트.
 * @param supportingText 텍스트 필드 아래에 표시될 보조 텍스트.
 * @param enabled 컴포넌트 활성화 여부. `false`일 경우 비활성화됩니다.
 */
@Composable
fun DialogDropdownMenu(
    options: List<String>,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedIndex: Int? = null,
    label: String? = null,
    placeholder: String = "",
    supportingText: String? = null,
    enabled: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }
    val initialText = options.getOrNull(selectedIndex ?: -1) ?: ""
    val textFieldState = rememberTextFieldState(initialText)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (enabled) expanded = it },
    ) {
        Column(modifier = modifier) {
            DialogTextField(
                label = label,
                readOnly = true,
                supportingText = supportingText,
                value = textFieldState.text.toString(),
                placeholder = placeholder,
                onValueChange = { textFieldState.setTextAndPlaceCursorAtEnd(it) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            )
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(text = option, style = DialogTheme.typography.bodyMedium) },
                    onClick = {
                        onSelectedIndexChange(index)
                        textFieldState.setTextAndPlaceCursorAtEnd(option)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogDropdownMenuPreviewLight() {
    DialogTheme {
        DialogDropdownMenuPreviewContent()
    }
}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
private fun DialogDropdownMenuPreviewDark() {
    DialogTheme(darkTheme = true) {
        DialogDropdownMenuPreviewContent()
    }
}

@Composable
private fun DialogDropdownMenuPreviewContent() {
    DialogDropdownMenu(
        options = listOf("안드로이드", "백엔드", "프론트엔드"),
        onSelectedIndexChange = {},
        label = "트랙",
        placeholder = "트랙을 선택해주세요",
        supportingText = "중복 선택 불가",
    )
}
