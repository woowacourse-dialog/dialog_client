package com.on.dialog.designsystem.icon.myiconpack

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

internal val Number: ImageVector
    get() {
        if (_number != null) {
            return _number!!
        }
        _number = Builder(
            name = "Number",
            defaultWidth = 18.0.dp,
            defaultHeight = 18.0.dp,
            viewportWidth = 48.0f,
            viewportHeight = 48.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 4.0f,
                strokeLineCap = Round,
                strokeLineJoin =
                    StrokeJoin.Companion.Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(9.0f, 4.0f)
                verticalLineTo(13.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 4.0f,
                strokeLineCap = Round,
                strokeLineJoin =
                    StrokeJoin.Companion.Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(12.0f, 13.0f)
                horizontalLineTo(6.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 4.0f,
                strokeLineCap = Round,
                strokeLineJoin =
                    StrokeJoin.Companion.Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(12.0f, 27.0f)
                horizontalLineTo(6.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 4.0f,
                strokeLineCap = Round,
                strokeLineJoin =
                    StrokeJoin.Companion.Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(6.0f, 20.0f)
                curveTo(6.0f, 20.0f, 9.0f, 17.0f, 11.0f, 20.0f)
                curveTo(13.0f, 23.0f, 6.0f, 27.0f, 6.0f, 27.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 4.0f,
                strokeLineCap = Round,
                strokeLineJoin =
                    StrokeJoin.Companion.Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(6.0f, 34.5f)
                curveTo(6.0f, 34.5f, 8.0f, 31.5f, 11.0f, 33.5f)
                curveTo(14.0f, 35.5f, 11.0f, 38.0f, 11.0f, 38.0f)
                curveTo(11.0f, 38.0f, 14.0f, 40.5f, 11.0f, 42.5f)
                curveTo(8.0f, 44.5f, 6.0f, 41.5f, 6.0f, 41.5f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 4.0f,
                strokeLineCap = Round,
                strokeLineJoin =
                    StrokeJoin.Companion.Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(11.0f, 38.0f)
                horizontalLineTo(9.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 4.0f,
                strokeLineCap = Round,
                strokeLineJoin =
                    StrokeJoin.Companion.Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(9.0f, 4.0f)
                lineTo(6.0f, 6.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 4.0f,
                strokeLineCap = Round,
                strokeLineJoin =
                    StrokeJoin.Companion.Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(21.0f, 24.0f)
                horizontalLineTo(43.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 4.0f,
                strokeLineCap = Round,
                strokeLineJoin =
                    StrokeJoin.Companion.Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(21.0f, 38.0f)
                horizontalLineTo(43.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 4.0f,
                strokeLineCap = Round,
                strokeLineJoin =
                    StrokeJoin.Companion.Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(21.0f, 10.0f)
                horizontalLineTo(43.0f)
            }
        }.build()
        return _number!!
    }

private var _number: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Number, contentDescription = "")
    }
}
