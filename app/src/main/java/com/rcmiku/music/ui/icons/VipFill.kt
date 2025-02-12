package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val VipFill: ImageVector
    get() {
        if (_VipFill != null) {
            return _VipFill!!
        }
        _VipFill = ImageVector.Builder(
            name = "VipFill",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(17.42f, 3f)
                arcToRelative(
                    2f,
                    2f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    1.649f,
                    0.868f
                )
                lineToRelative(0.087f, 0.14f)
                lineTo(22.49f, 9.84f)
                arcToRelative(
                    2f,
                    2f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.208f,
                    2.283f
                )
                lineToRelative(-0.114f, 0.123f)
                lineToRelative(-9.283f, 9.283f)
                arcToRelative(
                    1.25f,
                    1.25f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -1.666f,
                    0.091f
                )
                lineToRelative(-0.102f, -0.09f)
                lineToRelative(-9.283f, -9.284f)
                arcToRelative(
                    2f,
                    2f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.4f,
                    -2.257f
                )
                lineToRelative(0.078f, -0.15f)
                lineToRelative(3.333f, -5.832f)
                arcToRelative(
                    2f,
                    2f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    1.572f,
                    -1.001f
                )
                lineTo(6.58f, 3f)
                close()
                moveTo(7.293f, 9.293f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 1.414f)
                lineToRelative(3.823f, 3.823f)
                arcToRelative(
                    1.25f,
                    1.25f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    1.768f,
                    0f
                )
                lineToRelative(3.823f, -3.823f)
                arcToRelative(
                    1f,
                    1f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    -1.414f,
                    -1.414f
                )
                lineTo(12f, 12.586f)
                lineTo(8.707f, 9.293f)
                arcToRelative(
                    1f,
                    1f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -1.414f,
                    0f
                )
            }
        }.build()

        return _VipFill!!
    }

@Suppress("ObjectPropertyName")
private var _VipFill: ImageVector? = null
