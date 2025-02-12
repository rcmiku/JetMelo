package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SkipPrevious: ImageVector
    get() {
        if (_SkipPrevious != null) {
            return _SkipPrevious!!
        }
        _SkipPrevious = ImageVector.Builder(
            name = "SkipPrevious",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF5F6368))) {
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
                moveTo(660f, 480f)
                close()
                moveTo(660f, 570f)
                verticalLineToRelative(-180f)
                lineToRelative(-136f, 90f)
                lineToRelative(136f, 90f)
                close()
            }
        }.build()

        return _SkipPrevious!!
    }

@Suppress("ObjectPropertyName")
private var _SkipPrevious: ImageVector? = null
