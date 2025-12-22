package com.on.dialog.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.preview.SpacingPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Immutable
data class Spacing(
    val none: Dp = 0.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 12.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 24.dp,
    val extraExtraLarge: Dp = 32.dp,
    val huge: Dp = 48.dp,
)

internal val LocalSpacing = staticCompositionLocalOf { Spacing() }

@Preview(name = "Spacing Tokens", heightDp = 800)
@Composable
private fun SpacingPreview() {
    DialogTheme {
        SpacingPreview()
    }
}
