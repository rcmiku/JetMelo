package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val VipOne: ImageVector
    get() {
        if (_VipOne != null) {
            return _VipOne!!
        }
        _VipOne = ImageVector.Builder(
            name = "VipOne",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 48f,
            viewportHeight = 48f
        ).apply {
            group(
                clipPathData = PathData {
                    moveToRelative(4.503f, 16.366f)
                    lineToRelative(8.013f, -8.694f)
                    arcTo(
                        2.13f,
                        2.13f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        14.082f,
                        7f
                    )
                    horizontalLineToRelative(19.836f)
                    arcToRelative(
                        2.13f,
                        2.13f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        1.566f,
                        0.672f
                    )
                    lineToRelative(8.013f, 8.694f)
                    arcToRelative(
                        1.85f,
                        1.85f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        -0.035f,
                        2.572f
                    )
                    lineTo(24.75f, 40.15f)
                    arcToRelative(
                        1f,
                        1f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        -1.5f,
                        0f
                    )
                    lineTo(4.538f, 18.938f)
                    arcToRelative(
                        1.85f,
                        1.85f,
                        0f,
                        isMoreThanHalf = false,
                        isPositiveArc = true,
                        -0.035f,
                        -2.572f
                    )
                    moveTo(0f, 0f)
                    moveToRelative(16f, 20f)
                    lineToRelative(8f, 9f)
                    lineToRelative(8f, -9f)
                }
            ) {
                path(fill = SolidColor(Color(0xFF000000))) {
                    moveTo(0f, 0f)
                    horizontalLineToRelative(48f)
                    verticalLineToRelative(48f)
                    horizontalLineTo(0f)
                    close()
                }
            }
        }.build()

        return _VipOne!!
    }

@Suppress("ObjectPropertyName")
private var _VipOne: ImageVector? = null
