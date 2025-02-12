package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Pause: ImageVector
    get() {
        if (_Pause != null) {
            return _Pause!!
        }
        _Pause = ImageVector.Builder(
            name = "Pause",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF5F6368))) {
                moveTo(520f, 760f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(560f)
                lineTo(520f, 760f)
                close()
                moveTo(200f, 760f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(560f)
                lineTo(200f, 760f)
                close()
                moveTo(600f, 680f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(-400f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(400f)
                close()
                moveTo(280f, 680f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(-400f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(400f)
                close()
                moveTo(280f, 280f)
                verticalLineToRelative(400f)
                verticalLineToRelative(-400f)
                close()
                moveTo(600f, 280f)
                verticalLineToRelative(400f)
                verticalLineToRelative(-400f)
                close()
            }
        }.build()

        return _Pause!!
    }

@Suppress("ObjectPropertyName")
private var _Pause: ImageVector? = null
