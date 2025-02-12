package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val VipUser: ImageVector
    get() {
        if (_VipUser != null) {
            return _VipUser!!
        }
        _VipUser = ImageVector.Builder(
            name = "VipUser",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(2.338f, 4.863f)
                arcTo(1.25f, 1.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3.428f, 3f)
                horizontalLineToRelative(6.28f)
                curveToRelative(0.54f, 0f, 1.04f, 0.29f, 1.306f, 0.761f)
                lineTo(13f, 7.27f)
                lineToRelative(1.986f, -3.509f)
                arcTo(1.5f, 1.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 16.291f, 3f)
                horizontalLineToRelative(4.281f)
                arcToRelative(
                    1.25f,
                    1.25f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    1.09f,
                    1.863f
                )
                lineToRelative(-8.573f, 15.24f)
                arcToRelative(
                    1.25f,
                    1.25f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -2.179f,
                    0f
                )
                close()
                moveTo(4.71f, 5f)
                lineTo(12f, 17.96f)
                lineTo(19.29f, 5f)
                horizontalLineToRelative(-2.707f)
                lineTo(13f, 11.33f)
                lineTo(9.417f, 5f)
                close()
            }
        }.build()

        return _VipUser!!
    }

@Suppress("ObjectPropertyName")
private var _VipUser: ImageVector? = null
