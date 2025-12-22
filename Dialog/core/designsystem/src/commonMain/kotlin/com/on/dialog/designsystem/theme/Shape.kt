package com.on.dialog.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.on.dialog.designsystem.theme.preview.ShapePreview
import org.jetbrains.compose.ui.tooling.preview.Preview

internal val DialogShapes =
    Shapes(
        extraSmall = RoundedCornerShape(4.dp),
        small = RoundedCornerShape(8.dp),
        medium = RoundedCornerShape(12.dp),
        large = RoundedCornerShape(16.dp),
        extraLarge = RoundedCornerShape(24.dp),
    )

@Preview(name = "Shape Tokens", heightDp = 800)
@Composable
private fun ShapePreview() {
    DialogTheme {
        ShapePreview()
    }
}
