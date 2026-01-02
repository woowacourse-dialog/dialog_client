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
