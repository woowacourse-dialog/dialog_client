package com.on.dialog.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.DialogTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class DialogIconButtonTone { None, Primary }

/**
 * 아이콘만 표시하는 버튼으로 톤에 따라 배경을 다르게 렌더링합니다.
 *
 * @param onClick 버튼 클릭 콜백.
 * @param modifier 버튼 외부 Modifier.
 * @param enabled 비활성화 여부.
 * @param tone 배경 톤 설정.
 * @param shape 버튼 외곽 모양.
 * @param content 아이콘 콘텐츠 슬롯.
 */
@Composable
fun DialogIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tone: DialogIconButtonTone = DialogIconButtonTone.None,
    shape: Shape = DialogTheme.shapes.medium,
    content: @Composable () -> Unit,
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        content = content,
        shape = shape,
        modifier =
            modifier.background(
                shape = shape,
                color = dialogIconButtonBackground(
                    enabled = enabled,
                    tone = tone,
                ),
            ),
    )
}

@Composable
private fun dialogIconButtonBackground(
    enabled: Boolean,
    tone: DialogIconButtonTone,
): Color {
    val baseBackground = dialogIconButtonBaseBackground(tone)
    if (enabled) return baseBackground

    return baseBackground.copy(alpha = baseBackground.alpha * 0.6f)
}

@Composable
private fun dialogIconButtonBaseBackground(tone: DialogIconButtonTone): Color =
    when (tone) {
        DialogIconButtonTone.None -> Color.Transparent
        DialogIconButtonTone.Primary -> DialogTheme.colorScheme.primary.copy(alpha = 0.15f)
    }

@Preview(showBackground = true)
@Composable
private fun DialogIconButtonPreviewLight() {
    DialogTheme {
        DialogIconButtonPreviewContent()
    }
}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
private fun DialogIconButtonPreviewDark() {
    DialogTheme(darkTheme = true) {
        DialogIconButtonPreviewContent()
    }
}

@Composable
private fun DialogIconButtonPreviewContent() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DialogIconButton(onClick = {}, tone = DialogIconButtonTone.None) {
            Icon(Icons.Default.Close, null)
        }

        DialogIconButton(onClick = {}, tone = DialogIconButtonTone.Primary) {
            Icon(Icons.Default.Bookmark, null)
        }

        DialogIconButton(
            onClick = {},
            tone = DialogIconButtonTone.Primary,
            enabled = false,
        ) {
            Icon(Icons.Default.Bookmark, null)
        }
    }
}
