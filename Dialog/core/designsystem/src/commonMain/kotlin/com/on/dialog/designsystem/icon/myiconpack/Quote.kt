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

internal val Quote: ImageVector
    get() {
        if (_quote != null) {
            return _quote!!
        }
        _quote = Builder(
            name = "Quote", defaultWidth = 18.0.dp, defaultHeight = 18.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(3.0f, 5.0f)
                horizontalLineTo(21.0f)
                moveTo(10.0f, 10.0f)
                horizontalLineTo(21.0f)
                moveTo(10.0f, 14.0f)
                horizontalLineTo(21.0f)
                moveTo(3.0f, 19.0f)
                horizontalLineTo(21.0f)
                moveTo(3.0f, 9.0f)
                lineTo(6.0f, 12.0f)
                lineTo(3.0f, 15.0f)
            }
        }
            .build()
        return _quote!!
    }

private var _quote: ImageVector? = null

@Preview
@Composable
private fun Preview(): Unit {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Quote, contentDescription = "")
    }
}
