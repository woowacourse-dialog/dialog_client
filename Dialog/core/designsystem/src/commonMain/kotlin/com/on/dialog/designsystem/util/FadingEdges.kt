package com.on.dialog.designsystem.util

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class FadingEdgeDirection {
    Top,
    Bottom,
    Start,
    End,
    ;

    companion object {
        val Vertical = setOf(Top, Bottom)
        val Horizontal = setOf(Start, End)
    }
}

fun Modifier.drawFadingEdges(
    scrollableState: ScrollableState,
    fadingColor: Color = Color.Black,
    directions: Set<FadingEdgeDirection> = setOf(FadingEdgeDirection.Top),
    fadingSize: Dp = 4.dp,
): Modifier = then(
    Modifier
        .graphicsLayer(
            compositingStrategy = CompositingStrategy.Offscreen,
        ).drawWithContent {
            drawContent()
            val fadingSizePx = fadingSize.toPx()
            require(fadingSizePx >= 1f) { "잘못된 fadingSizePx 값입니다. 최소값: 1f, 입력값: $fadingSizePx" }

            if (directions.contains(FadingEdgeDirection.Top) && scrollableState.canScrollBackward) {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, fadingColor),
                        startY = 0f,
                        endY = fadingSizePx,
                    ),
                    blendMode = BlendMode.DstIn,
                )
            }

            if (directions.contains(FadingEdgeDirection.Bottom) && scrollableState.canScrollForward) {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(fadingColor, Color.Transparent),
                        startY = size.height - fadingSizePx,
                        endY = size.height,
                    ),
                    blendMode = BlendMode.DstIn,
                )
            }

            if (directions.contains(FadingEdgeDirection.Start) && scrollableState.canScrollBackward) {
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, fadingColor),
                        startX = 0f,
                        endX = fadingSizePx,
                    ),
                    blendMode = BlendMode.DstIn,
                )
            }

            if (directions.contains(FadingEdgeDirection.End) && scrollableState.canScrollForward) {
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(fadingColor, Color.Transparent),
                        startX = size.width - fadingSizePx,
                        endX = size.width,
                    ),
                    blendMode = BlendMode.DstIn,
                )
            }
        },
)
