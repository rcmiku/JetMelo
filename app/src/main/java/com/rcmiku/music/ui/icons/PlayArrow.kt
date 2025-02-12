package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val PlayArrow: ImageVector
    get() {
        if (_PlayArrow != null) {
            return _PlayArrow!!
        }
        _PlayArrow = ImageVector.Builder(
            name = "PlayArrow",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF5F6368))) {
                moveTo(320f, 760f)
                verticalLineToRelative(-560f)
                lineToRelative(440f, 280f)
                lineToRelative(-440f, 280f)
                close()
                moveTo(400f, 480f)
                close()
                moveTo(400f, 614f)
                lineTo(610f, 480f)
                lineTo(400f, 346f)
                verticalLineToRelative(268f)
                close()
            }
        }.build()

        return _PlayArrow!!
    }

@Suppress("ObjectPropertyName")
private var _PlayArrow: ImageVector? = null
