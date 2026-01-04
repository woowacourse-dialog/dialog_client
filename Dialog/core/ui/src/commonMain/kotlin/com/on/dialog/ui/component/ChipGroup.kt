package com.on.dialog.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.component.DialogChip
import com.on.dialog.designsystem.theme.DialogTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * [DialogChip] 컴포넌트에 필요한 데이터를 나타내는 클래스
 *
 * @property text 칩에 표시될 텍스트.
 * @property textColor 칩 텍스트의 색상.
 * @property backgroundColor 칩의 배경 색상.
 */
@Immutable
data class ChipCategory(
    val text: String,
    val textColor: Color,
    val backgroundColor: Color,
)

/**
 * 여러 개의 DialogInputChip을 행 단위로 배치하고 삭제 콜백을 연결합니다.
 *
 * @param chips 표시할 ChipCategory 리스트.
 * @param onChipsChange 칩 목록이 변경되었을 때 호출되는 콜백.
 * @param readOnly true일 경우 삭제 아이콘을 표시하지 않습니다.
 * @param modifier FlowRow에 적용할 Modifier.
 */
@Composable
fun DialogChipGroup(
    chips: ImmutableList<ChipCategory>,
    onChipsChange: (List<ChipCategory>) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = true,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small),
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small),
    ) {
        chips.forEach { chip ->
            DialogChip(
                text = chip.text,
                textColor = chip.textColor,
                backgroundColor = chip.backgroundColor,
                onRemove =
                    if (readOnly) {
                        null
                    } else {
                        { onChipsChange(chips - chip) }
                    },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogChipGroupPreview() {
    var chips by remember {
        mutableStateOf(
            listOf(
                ChipCategory(
                    text = "Android",
                    textColor = Color(0xFF003D2E),
                    backgroundColor = Color(0xFF3DDC84),
                ),
                ChipCategory(
                    text = "Backend",
                    textColor = Color.White,
                    backgroundColor = Color(0xFFFF6F00),
                ),
                ChipCategory(
                    text = "Frontend",
                    textColor = Color.White,
                    backgroundColor = Color(0xFF2196F3),
                ),
            ),
        )
    }
    DialogTheme {
        DialogChipGroup(
            chips = chips.toImmutableList(),
            onChipsChange = { chips = it },
            modifier = Modifier.padding(16.dp),
        )
    }
}
