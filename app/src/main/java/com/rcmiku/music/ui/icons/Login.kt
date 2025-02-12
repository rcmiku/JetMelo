package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Login: ImageVector
    get() {
        if (_Login != null) {
            return _Login!!
        }
        _Login = ImageVector.Builder(
            name = "Login",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(480f, 840f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(280f)
                verticalLineToRelative(-560f)
                lineTo(480f, 200f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(280f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(840f, 200f)
                verticalLineToRelative(560f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(760f, 840f)
                lineTo(480f, 840f)
                close()
                moveTo(400f, 680f)
                lineTo(345f, 622f)
                lineTo(447f, 520f)
                lineTo(120f, 520f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(327f)
                lineTo(345f, 338f)
                lineToRelative(55f, -58f)
                lineToRelative(200f, 200f)
                lineToRelative(-200f, 200f)
                close()
            }
        }.build()

        return _Login!!
    }

@Suppress("ObjectPropertyName")
private var _Login: ImageVector? = null
