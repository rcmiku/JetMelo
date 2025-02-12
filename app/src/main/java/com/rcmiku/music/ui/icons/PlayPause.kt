package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val PlayPause: ImageVector
    get() {
        if (_PlayPause != null) {
            return _PlayPause!!
        }
        _PlayPause = ImageVector.Builder(
            name = "PlayPause",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF5F6368))) {
                moveTo(200f, 648f)
                verticalLineToRelative(-336f)
                lineToRelative(240f, 168f)
                lineToRelative(-240f, 168f)
                close()
                moveTo(520f, 640f)
                verticalLineToRelative(-320f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(320f)
                horizontalLineToRelative(-80f)
                close()
                moveTo(680f, 640f)
                verticalLineToRelative(-320f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(320f)
                horizontalLineToRelative(-80f)
                close()
            }
        }.build()

        return _PlayPause!!
    }

@Suppress("ObjectPropertyName")
private var _PlayPause: ImageVector? = null
