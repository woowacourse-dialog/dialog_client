package com.on.dialog.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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

/**
 * [DialogChip] 컴포넌트에 필요한 데이터를 나타내는 클래스
 *
 * @property text 칩에 표시될 텍스트.
 * @property textColor 칩 텍스트의 색상.
 * @property backgroundColor 칩의 배경 색상.
 */
data class ChipCategory(
    val text: String,
    val textColor: Color,
    val backgroundColor: Color,
)

/**
 * @param chip 표시할 칩의 데이터를 담고 있는 [ChipCategory] 객체.
 * @param modifier 이 컴포넌트에 적용할 [Modifier].
 * @param onRemove 삭제 아이콘 터치 시 호출되는 콜백, null이면 아이콘을 숨깁니다.
 */
@Composable
fun DialogChip(
    chip: ChipCategory,
    modifier: Modifier = Modifier,
    onRemove: (() -> Unit)? = null,
) {
    DialogChip(
        text = chip.text,
        textColor = chip.textColor,
        backgroundColor = chip.backgroundColor,
        modifier = modifier,
        onRemove = onRemove,
    )
}

@Composable
fun DialogChip(
    text: String,
    textColor: Color = Color.White,
    backgroundColor: Color = DialogTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
    onRemove: (() -> Unit)? = null,
) {
    Surface(
        modifier = modifier,
        shape = DialogTheme.shapes.large,
        color = backgroundColor,
        contentColor = textColor,
        tonalElevation = 0.dp,
    ) {
        Row(
            modifier =
                Modifier.padding(
                    horizontal = DialogTheme.spacing.small,
                    vertical = DialogTheme.spacing.small,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                style = DialogTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
            )
            if (onRemove != null) {
                Spacer(Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "삭제",
                    modifier =
                        Modifier
                            .size(12.dp)
                            .clip(DialogTheme.shapes.medium)
                            .clickable { onRemove() },
                )
            }
        }
    }
}

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
    chips: List<ChipCategory>,
    onChipsChange: (List<ChipCategory>) -> Unit,
    readOnly: Boolean = true,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small),
        verticalArrangement = Arrangement.spacedBy(DialogTheme.spacing.small),
    ) {
        chips.forEach { chip ->
            DialogChip(
                chip = chip,
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
private fun DialogChipPreview() {
    DialogTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DialogChip(
                chip = ChipCategory(
                    text = "Android",
                    textColor = Color(0xFF003D2E),
                    backgroundColor = Color(0xFF3DDC84),
                ),
            )
            Spacer(Modifier.height(8.dp))
            DialogChip(
                chip = ChipCategory(
                    text = "Backend",
                    textColor = Color.White,
                    backgroundColor = Color(0xFFFF6F00),
                ),
            )
            Spacer(Modifier.height(8.dp))
            DialogChip(
                chip = ChipCategory(
                    text = "Frontend",
                    textColor = Color.White,
                    backgroundColor = Color(0xFF2196F3),
                ),
            )
            Spacer(Modifier.height(8.dp))
            DialogChip(text = "#기본 스타일")
            Spacer(Modifier.height(8.dp))
            DialogChip(text = "#기본 스타일", onRemove = {})
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
            chips = chips,
            onChipsChange = { chips = it },
            modifier = Modifier.padding(16.dp),
        )
    }
}
