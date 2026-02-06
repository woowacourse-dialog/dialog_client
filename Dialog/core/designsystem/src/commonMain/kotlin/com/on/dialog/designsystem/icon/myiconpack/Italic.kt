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

internal val Italic: ImageVector
    get() {
        if (_italic != null) {
            return _italic!!
        }
        _italic = Builder(
            name = "Italic",
            defaultWidth = 18.0.dp,
            defaultHeight = 18.0.dp,
            viewportWidth = 32.0f,
            viewportHeight = 32.0f,
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
                moveTo(22.0f, 4.0f)
                horizontalLineToRelative(-8.0f)
                curveToRelative(-0.6f, 0.0f, -1.0f, 0.4f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.4f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(2.8f)
                lineToRelative(-3.6f, 20.0f)
                horizontalLineTo(10.0f)
                curveToRelative(-0.6f, 0.0f, -1.0f, 0.4f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.4f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(8.0f)
                curveToRelative(0.6f, 0.0f, 1.0f, -0.4f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.4f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-2.8f)
                lineToRelative(3.6f, -20.0f)
                horizontalLineTo(22.0f)
                curveToRelative(0.6f, 0.0f, 1.0f, -0.4f, 1.0f, -1.0f)
                reflectiveCurveTo(22.6f, 4.0f, 22.0f, 4.0f)
                close()
            }
        }.build()
        return _italic!!
    }

private var _italic: ImageVector? = null

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Italic, contentDescription = "")
    }
}
