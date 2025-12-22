package com.on.dialog.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.preview.ShadowPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ShadowLevel(
    internal val radius: Dp,
    internal val spread: Dp,
    internal val offsetX: Dp,
    internal val offsetY: Dp,
    internal val color: Color,
) {
    NONE(0.dp, 0.dp, 4.dp, 4.dp, Color.Transparent),
    SMALL(4.dp, 2.dp, 4.dp, 4.dp, Color.Black.copy(alpha = 0.1f)),
    MEDIUM(8.dp, 4.dp, 4.dp, 4.dp, Color.Black.copy(alpha = 0.15f)),
    LARGE(12.dp, 6.dp, 4.dp, 4.dp, Color.Black.copy(alpha = 0.2f)),
    EXTRA_LARGE(16.dp, 8.dp, 4.dp, 4.dp, Color.Black.copy(alpha = 0.25f)),
}

fun Modifier.dropShadow(
    level: ShadowLevel,
    shape: Shape = RectangleShape,
) = this.dropShadow(
    shape = shape,
    shadow = Shadow(
        radius = level.radius,
        spread = level.spread,
        color = level.color,
        offset = DpOffset(x = level.offsetX, y = level.offsetY),
    ),
)

@Preview(name = "Shadow Tokens", heightDp = 800)
@Composable
private fun ShadowPreview() {
    DialogTheme {
        ShadowPreview()
    }
}
