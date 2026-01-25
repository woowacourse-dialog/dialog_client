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

internal val Codeblock: ImageVector
    get() {
        if (_codeblock != null) {
            return _codeblock!!
        }
        _codeblock = Builder(
            name = "Codeblock", defaultWidth = 18.0.dp, defaultHeight = 18.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(22.0f, 9.0f)
                horizontalLineTo(2.0f)
                moveTo(14.0f, 17.5f)
                lineTo(16.5f, 15.0f)
                lineTo(14.0f, 12.5f)
                moveTo(10.0f, 12.5f)
                lineTo(7.5f, 15.0f)
                lineTo(10.0f, 17.5f)
                moveTo(2.0f, 7.8f)
                lineTo(2.0f, 16.2f)
                curveTo(2.0f, 17.88f, 2.0f, 18.72f, 2.327f, 19.362f)
                curveTo(2.615f, 19.927f, 3.074f, 20.385f, 3.638f, 20.673f)
                curveTo(4.28f, 21.0f, 5.12f, 21.0f, 6.8f, 21.0f)
                horizontalLineTo(17.2f)
                curveTo(18.88f, 21.0f, 19.72f, 21.0f, 20.362f, 20.673f)
                curveTo(20.927f, 20.385f, 21.385f, 19.927f, 21.673f, 19.362f)
                curveTo(22.0f, 18.72f, 22.0f, 17.88f, 22.0f, 16.2f)
                verticalLineTo(7.8f)
                curveTo(22.0f, 6.12f, 22.0f, 5.28f, 21.673f, 4.638f)
                curveTo(21.385f, 4.074f, 20.927f, 3.615f, 20.362f, 3.327f)
                curveTo(19.72f, 3.0f, 18.88f, 3.0f, 17.2f, 3.0f)
                lineTo(6.8f, 3.0f)
                curveTo(5.12f, 3.0f, 4.28f, 3.0f, 3.638f, 3.327f)
                curveTo(3.074f, 3.615f, 2.615f, 4.074f, 2.327f, 4.638f)
                curveTo(2.0f, 5.28f, 2.0f, 6.12f, 2.0f, 7.8f)
                close()
            }
        }
            .build()
        return _codeblock!!
    }

private var _codeblock: ImageVector? = null

@Preview
@Composable
private fun Preview(): Unit {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Codeblock, contentDescription = "")
    }
}
