package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ArrowInsert: ImageVector
    get() {
        if (_ArrowInsert != null) {
            return _ArrowInsert!!
        }
        _ArrowInsert = ImageVector.Builder(
            name = "ArrowInsert",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(704f, 720f)
                lineTo(320f, 336f)
                verticalLineToRelative(344f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(-480f)
                horizontalLineToRelative(480f)
                verticalLineToRelative(80f)
                lineTo(376f, 280f)
                lineToRelative(384f, 384f)
                lineToRelative(-56f, 56f)
                close()
            }
        }.build()

        return _ArrowInsert!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowInsert: ImageVector? = null
