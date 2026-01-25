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
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

internal val Link: ImageVector
    get() {
        if (_link != null) {
            return _link!!
        }
        _link = Builder(
            name = "Link", defaultWidth = 18.0.dp, defaultHeight = 18.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(9.165f, 17.65f)
                curveTo(8.925f, 17.874f, 8.74f, 18.024f, 8.55f, 18.134f)
                curveTo(7.622f, 18.67f, 6.478f, 18.67f, 5.55f, 18.134f)
                curveTo(5.208f, 17.936f, 4.879f, 17.607f, 4.222f, 16.95f)
                curveTo(3.564f, 16.292f, 3.235f, 15.963f, 3.038f, 15.621f)
                curveTo(2.502f, 14.693f, 2.502f, 13.55f, 3.038f, 12.621f)
                curveTo(3.235f, 12.279f, 3.564f, 11.95f, 4.222f, 11.293f)
                lineTo(7.05f, 8.464f)
                curveTo(7.708f, 7.807f, 8.036f, 7.478f, 8.378f, 7.281f)
                curveTo(9.307f, 6.745f, 10.45f, 6.745f, 11.378f, 7.281f)
                curveTo(11.72f, 7.478f, 12.049f, 7.807f, 12.707f, 8.464f)
                curveTo(13.364f, 9.122f, 13.693f, 9.451f, 13.891f, 9.793f)
                curveTo(14.427f, 10.721f, 14.427f, 11.865f, 13.891f, 12.793f)
                curveTo(13.781f, 12.983f, 13.631f, 13.168f, 13.408f, 13.408f)
                moveTo(10.592f, 10.592f)
                curveTo(10.368f, 10.832f, 10.218f, 11.017f, 10.109f, 11.207f)
                curveTo(9.573f, 12.135f, 9.573f, 13.279f, 10.109f, 14.207f)
                curveTo(10.306f, 14.549f, 10.635f, 14.878f, 11.293f, 15.535f)
                curveTo(11.95f, 16.193f, 12.279f, 16.522f, 12.621f, 16.719f)
                curveTo(13.549f, 17.255f, 14.693f, 17.255f, 15.621f, 16.719f)
                curveTo(15.963f, 16.522f, 16.292f, 16.193f, 16.949f, 15.535f)
                lineTo(19.778f, 12.707f)
                curveTo(20.435f, 12.05f, 20.764f, 11.721f, 20.962f, 11.379f)
                curveTo(21.498f, 10.45f, 21.498f, 9.307f, 20.962f, 8.379f)
                curveTo(20.764f, 8.037f, 20.435f, 7.708f, 19.778f, 7.05f)
                curveTo(19.12f, 6.393f, 18.792f, 6.064f, 18.449f, 5.866f)
                curveTo(17.521f, 5.331f, 16.378f, 5.331f, 15.45f, 5.866f)
                curveTo(15.26f, 5.976f, 15.074f, 6.126f, 14.835f, 6.35f)
            }
        }
            .build()
        return _link!!
    }

private var _link: ImageVector? = null

@Preview
@Composable
private fun Preview(): Unit {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Link, contentDescription = "")
    }
}
