package com.on.dialog.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
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
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                Text(
                    text = "Shadow Tokens",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                ShadowItem("NONE", ShadowLevel.NONE)
                ShadowItem("SMALL", ShadowLevel.SMALL)
                ShadowItem("MEDIUM", ShadowLevel.MEDIUM)
                ShadowItem("LARGE", ShadowLevel.LARGE)
                ShadowItem("EXTRA_LARGE", ShadowLevel.EXTRA_LARGE)
            }
        }
    }
}

@Composable
private fun ShadowItem(
    name: String,
    level: ShadowLevel,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
        )
        Box(
            modifier = Modifier
                .size(80.dp)
                .dropShadow(level = level, shape = MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}
