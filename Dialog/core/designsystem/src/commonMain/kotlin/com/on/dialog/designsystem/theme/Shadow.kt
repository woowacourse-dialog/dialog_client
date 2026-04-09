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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 컴포넌트에 적용되는 그림자 강도를 정의하는 열거형입니다.
 *
 * 각 ShadowLevel은 그림자의 흐림 정도, 위치, 크기, 투명도를 조합하여
 * 시각적인 높이와 깊이감을 표현합니다.
 */
enum class ShadowLevel(
    /**
     * 그림자의 블러 반경입니다.
     *
     * 값이 클수록 그림자가 더 넓고 부드럽게 퍼지며,
     * 작을수록 또렷하고 날카로운 그림자가 표현됩니다.
     */
    val blur: Dp,
    /**
     * 그림자의 가로 방향 오프셋입니다.
     *
     * - 양수 : 오른쪽으로 이동
     * - 음수 : 왼쪽으로 이동
     */
    val offsetX: Dp,
    /**
     * 그림자의 세로 방향 오프셋입니다.
     *
     * - 양수 : 아래쪽으로 이동
     * - 음수 : 위쪽으로 이동
     *
     * 일반적으로 값이 클수록 컴포넌트가 떠 있는 것처럼 보입니다.
     */
    val offsetY: Dp,
    /**
     * 그림자의 투명도 값입니다.
     *
     * `0f`에 가까울수록 투명하며,
     * `1f`에 가까울수록 진한 그림자가 표현됩니다.
     */
    val alpha: Float,
) {
    DEFAULT(
        blur = 4.dp,
        offsetX = 0.dp,
        offsetY = 2.dp,
        alpha = 0.18f,
    ),
}

@Composable
fun Modifier.dropShadow(
    shape: Shape,
    level: ShadowLevel = ShadowLevel.DEFAULT,
    color: Color = DialogTheme.colorScheme.onSurface,
) = this.dropShadowCache(
    color = color.copy(alpha = level.alpha),
    offsetX = level.offsetX,
    offsetY = level.offsetY,
    blurRadius = level.blur,
    shape = shape,
)

internal expect fun NativePaint.setMaskFilter(blurRadius: Float)

fun Modifier.dropShadowCache(
    shape: Shape,
    color: Color,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 2.dp,
    blurRadius: Dp = 4.dp,
): Modifier =
    drawWithCache {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        if (blurRadius != 0.dp) {
            frameworkPaint.setMaskFilter(blurRadius.value)
        }
        frameworkPaint.color = color.toArgb()

        val leftPixel = offsetX.toPx()
        val topPixel = offsetY.toPx()

        val path = Path().apply {
            addOutline(shape.createOutline(size, layoutDirection, this@drawWithCache))
        }

        onDrawBehind {
            drawIntoCanvas { canvas ->
                canvas.translate(leftPixel, topPixel)
                canvas.drawPath(path, paint)
                canvas.translate(-leftPixel, -topPixel)
            }
        }
    }.clip(shape)

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

                ShadowItem("DEFAULT", ShadowLevel.DEFAULT)
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
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
        )
        Box(
            modifier = Modifier
                .size(80.dp)
                .dropShadow(level = level, shape = RectangleShape)
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
