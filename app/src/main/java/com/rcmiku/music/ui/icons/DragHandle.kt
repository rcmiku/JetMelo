package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val DragHandle: ImageVector
    get() {
        if (_DragHandle != null) {
            return _DragHandle!!
        }
        _DragHandle = ImageVector.Builder(
            name = "DragHandle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF5F6368))) {
                moveTo(160f, 600f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(640f)
                verticalLineToRelative(80f)
                lineTo(160f, 600f)
                close()
                moveTo(160f, 440f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(640f)
                verticalLineToRelative(80f)
                lineTo(160f, 440f)
                close()
            }
        }.build()

        return _DragHandle!!
    }

@Suppress("ObjectPropertyName")
private var _DragHandle: ImageVector? = null
