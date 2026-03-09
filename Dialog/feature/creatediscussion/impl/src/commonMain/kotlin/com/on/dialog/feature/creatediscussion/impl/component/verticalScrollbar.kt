package com.on.dialog.feature.creatediscussion.impl.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.verticalScrollbar(
    scrollState: ScrollState,
    scrollbarWidth: Dp = 4.dp,
    color: Color = Color.Gray.copy(alpha = 0.6f),
    cornerRadius: Dp = 2.dp,
    showAlways: Boolean = false,
): Modifier = composed {
    val scrollbarAlpha by animateFloatAsState(
        targetValue = if (showAlways || scrollState.isScrollInProgress) 1f else 0f,
        animationSpec = tween(
            durationMillis = 150,
            delayMillis = if (scrollState.isScrollInProgress) 0 else 500
        ),
        label = "scrollbar"
    )

    drawWithContent {
        drawContent()

        val viewableHeight = size.height
        val totalHeight = viewableHeight + scrollState.maxValue

        if (totalHeight <= viewableHeight) return@drawWithContent

        val thumbHeight = (viewableHeight / totalHeight) * viewableHeight
        val thumbY =
            (scrollState.value.toFloat() / scrollState.maxValue) * (viewableHeight - thumbHeight)

        drawRoundRect(
            color = color.copy(alpha = color.alpha * scrollbarAlpha),
            topLeft = Offset(size.width - scrollbarWidth.toPx(), thumbY),
            size = Size(scrollbarWidth.toPx(), thumbHeight),
            cornerRadius = CornerRadius(cornerRadius.toPx()),
        )
    }
}