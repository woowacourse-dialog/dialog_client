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

internal val Bullet: ImageVector
    get() {
        if (_bullet != null) {
            return _bullet!!
        }
        _bullet = Builder(
            name = "Bullet",
            defaultWidth = 800.0.dp,
            defaultHeight = 800.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2.0f,
                strokeLineCap = Round,
                strokeLineJoin =
                    StrokeJoin.Companion.Round,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(9.0f, 17.0f)
                horizontalLineTo(19.0f)
                moveTo(9.0f, 12.0f)
                horizontalLineTo(19.0f)
                moveTo(9.0f, 7.0f)
                horizontalLineTo(19.0f)
                moveTo(5.002f, 17.0f)
                verticalLineTo(17.002f)
                lineTo(5.0f, 17.002f)
                verticalLineTo(17.0f)
                horizontalLineTo(5.002f)
                close()
                moveTo(5.002f, 12.0f)
                verticalLineTo(12.002f)
                lineTo(5.0f, 12.002f)
                verticalLineTo(12.0f)
                horizontalLineTo(5.002f)
                close()
                moveTo(5.002f, 7.0f)
                verticalLineTo(7.002f)
                lineTo(5.0f, 7.002f)
                verticalLineTo(7.0f)
                horizontalLineTo(5.002f)
                close()
            }
        }.build()
        return _bullet!!
    }

private var _bullet: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Bullet, contentDescription = "")
    }
}
