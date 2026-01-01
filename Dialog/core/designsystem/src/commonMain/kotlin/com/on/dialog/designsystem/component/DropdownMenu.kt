@file:OptIn(ExperimentalMaterial3Api::class)

package com.on.dialog.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.DialogTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DialogDropdownMenu(
    options: List<String>,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedIndex: Int? = null,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }
    val initialText = options.getOrNull(selectedIndex ?: -1) ?: placeholder ?: ""
    val textFieldState = rememberTextFieldState(initialText)

    Column(modifier = modifier) {
        Text(
            text = label ?: "",
            style = DialogTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(DialogTheme.spacing.extraSmall),
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { if (enabled) expanded = it },
        ) {
            BasicTextField(
                state = textFieldState,
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
                    .clip(DialogTheme.shapes.small)
                    .background(Color.LightGray),
                readOnly = true,
                enabled = enabled,
                textStyle = DialogTheme.typography.bodyLarge,
                lineLimits = TextFieldLineLimits.SingleLine,
                decorator = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = DialogTheme.spacing.medium,
                                vertical = DialogTheme.spacing.small,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        innerTextField()
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        text = { Text(text = option, style = DialogTheme.typography.bodyMedium) },
                        onClick = {
                            onSelectedIndexChange(index)
                            textFieldState.setTextAndPlaceCursorAtEnd(options[index])
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogDropdownMenuPreview() {
    DialogTheme {
        DialogDropdownMenu(
            options = listOf("안드로이드", "백엔드", "프론트엔드"),
            onSelectedIndexChange = {},
            label = "트랙",
            placeholder = "트랙을 선택해주세요",
        )
    }
}
