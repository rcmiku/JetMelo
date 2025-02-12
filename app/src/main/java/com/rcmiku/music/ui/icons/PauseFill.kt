package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val PauseFill: ImageVector
    get() {
        if (_PauseFill != null) {
            return _PauseFill!!
        }
        _PauseFill = ImageVector.Builder(
            name = "PauseFill",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(560f, 760f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(560f)
                lineTo(560f, 760f)
                close()
                moveTo(240f, 760f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(560f)
                lineTo(240f, 760f)
                close()
            }
        }.build()

        return _PauseFill!!
    }

@Suppress("ObjectPropertyName")
private var _PauseFill: ImageVector? = null
