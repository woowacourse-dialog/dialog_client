package com.on.dialog.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.preview.ThemePreview
import com.on.dialog.designsystem.theme.DialogTheme

enum class DividerOrientation {
    Horizontal,
    Vertical,
}

@Composable
fun DialogDivider(
    orientation: DividerOrientation,
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
) {
    when (orientation) {
        DividerOrientation.Horizontal -> HorizontalDivider(
            modifier = modifier,
            thickness = thickness,
            color = color,
        )

        DividerOrientation.Vertical -> VerticalDivider(
            modifier = modifier,
            thickness = thickness,
            color = color,
        )
    }
}

@Composable
fun DialogHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
) {
    DialogDivider(
        orientation = DividerOrientation.Horizontal,
        modifier = modifier.fillMaxWidth(),
        thickness = thickness,
        color = color,
    )
}

@Composable
fun DialogVerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
) {
    DialogDivider(
        orientation = DividerOrientation.Vertical,
        modifier = modifier.fillMaxHeight(),
        thickness = thickness,
        color = color,
    )
}

@ThemePreview
@Composable
private fun DialogHorizontalDividerPreview() {
    DialogTheme {
        Surface {
            Box(modifier = Modifier.width(100.dp).padding(12.dp)) {
                DialogHorizontalDivider()
            }
        }
    }
}

@ThemePreview
@Composable
private fun DialogVerticalDividerPreview() {
    DialogTheme {
        Surface {
            Box(modifier = Modifier.height(100.dp).padding(12.dp)) {
                DialogVerticalDivider()
            }
        }
    }
}
