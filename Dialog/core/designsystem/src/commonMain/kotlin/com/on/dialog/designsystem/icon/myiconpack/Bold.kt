package com.on.dialog.designsystem.icon.myiconpack

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

internal val Bold: ImageVector
    get() {
        if (_bold != null) {
            return _bold!!
        }
        _bold = Builder(
            name = "Bold",
            defaultWidth = 18.0.dp,
            defaultHeight = 18.0.dp,
            viewportWidth = 800.0f,
            viewportHeight = 800.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(548.57f, 395.6f)
                curveTo(564.67f, 381.65f, 577.59f, 364.41f, 586.46f, 345.05f)
                curveTo(595.33f, 325.68f, 599.95f, 304.64f, 600.0f, 283.33f)
                curveTo(599.95f, 234.73f, 580.61f, 188.13f, 546.24f, 153.76f)
                curveTo(511.87f, 119.39f, 465.27f, 100.05f, 416.67f, 100.0f)
                horizontalLineTo(166.67f)
                verticalLineTo(733.33f)
                horizontalLineTo(450.0f)
                curveTo(489.65f, 733.35f, 528.24f, 720.5f, 559.96f, 696.71f)
                curveTo(591.69f, 672.93f, 614.84f, 639.5f, 625.95f, 601.43f)
                curveTo(637.06f, 563.37f, 635.52f, 522.73f, 621.57f, 485.62f)
                curveTo(607.62f, 448.5f, 582.0f, 416.92f, 548.57f, 395.6f)
                close()
                moveTo(266.67f, 200.0f)
                horizontalLineTo(416.67f)
                curveTo(438.77f, 200.0f, 459.96f, 208.78f, 475.59f, 224.41f)
                curveTo(491.22f, 240.04f, 500.0f, 261.23f, 500.0f, 283.33f)
                curveTo(500.0f, 305.43f, 491.22f, 326.63f, 475.59f, 342.26f)
                curveTo(459.96f, 357.89f, 438.77f, 366.67f, 416.67f, 366.67f)
                horizontalLineTo(266.67f)
                verticalLineTo(200.0f)
                close()
                moveTo(450.0f, 633.33f)
                horizontalLineTo(266.67f)
                verticalLineTo(466.67f)
                horizontalLineTo(450.0f)
                curveTo(472.1f, 466.67f, 493.3f, 475.45f, 508.93f, 491.07f)
                curveTo(524.55f, 506.7f, 533.33f, 527.9f, 533.33f, 550.0f)
                curveTo(533.33f, 572.1f, 524.55f, 593.3f, 508.93f, 608.93f)
                curveTo(493.3f, 624.55f, 472.1f, 633.33f, 450.0f, 633.33f)
                close()
            }
        }.build()
        return _bold!!
    }

private var _bold: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Bold, contentDescription = "")
    }
}
