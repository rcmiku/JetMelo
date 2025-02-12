package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SkipNextFill: ImageVector
    get() {
        if (_SkipNextFill != null) {
            return _SkipNextFill!!
        }
        _SkipNextFill = ImageVector.Builder(
            name = "SkipNextFill",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(660f, 720f)
                verticalLineToRelative(-480f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(480f)
                horizontalLineToRelative(-80f)
                close()
                moveTo(220f, 720f)
                verticalLineToRelative(-480f)
                lineToRelative(360f, 240f)
                lineToRelative(-360f, 240f)
                close()
            }
        }.build()

        return _SkipNextFill!!
    }

@Suppress("ObjectPropertyName")
private var _SkipNextFill: ImageVector? = null
