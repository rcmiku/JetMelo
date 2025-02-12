package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SkipPreviousFill: ImageVector
    get() {
        if (_SkipPreviousFill != null) {
            return _SkipPreviousFill!!
        }
        _SkipPreviousFill = ImageVector.Builder(
            name = "SkipPreviousFill",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(220f, 720f)
                verticalLineToRelative(-480f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(480f)
                horizontalLineToRelative(-80f)
                close()
                moveTo(740f, 720f)
                lineTo(380f, 480f)
                lineToRelative(360f, -240f)
                verticalLineToRelative(480f)
                close()
            }
        }.build()

        return _SkipPreviousFill!!
    }

@Suppress("ObjectPropertyName")
private var _SkipPreviousFill: ImageVector? = null
