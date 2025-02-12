package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SongListAdd: ImageVector
    get() {
        if (_SongListAdd != null) {
            return _SongListAdd!!
        }
        _SongListAdd = ImageVector.Builder(
            name = "SongListAdd",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(700f, 840f)
                horizontalLineToRelative(40f)
                verticalLineToRelative(-100f)
                horizontalLineToRelative(100f)
                verticalLineToRelative(-40f)
                lineTo(740f, 700f)
                verticalLineToRelative(-100f)
                horizontalLineToRelative(-40f)
                verticalLineToRelative(100f)
                lineTo(600f, 700f)
                verticalLineToRelative(40f)
                horizontalLineToRelative(100f)
                verticalLineToRelative(100f)
                close()
                moveTo(720f, 920f)
                quadToRelative(-83f, 0f, -141.5f, -58.5f)
                reflectiveQuadTo(520f, 720f)
                quadToRelative(0f, -83f, 58.5f, -141.5f)
                reflectiveQuadTo(720f, 520f)
                quadToRelative(83f, 0f, 141.5f, 58.5f)
                reflectiveQuadTo(920f, 720f)
                quadToRelative(0f, 83f, -58.5f, 141.5f)
                reflectiveQuadTo(720f, 920f)
                close()
                moveTo(360f, 280f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(480f)
                verticalLineToRelative(80f)
                lineTo(360f, 280f)
                close()
                moveTo(200f, 800f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(120f, 720f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(200f, 640f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(280f, 720f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(200f, 800f)
                close()
                moveTo(200f, 560f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(120f, 480f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(200f, 400f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(280f, 480f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(200f, 560f)
                close()
                moveTo(200f, 320f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(120f, 240f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(200f, 160f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(280f, 240f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(200f, 320f)
                close()
                moveTo(360f, 760f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(83f)
                quadToRelative(-3f, 20f, -3f, 40f)
                reflectiveQuadToRelative(3f, 40f)
                horizontalLineToRelative(-83f)
                close()
                moveTo(360f, 520f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(360f)
                quadToRelative(-57f, 0f, -107f, 21.5f)
                reflectiveQuadTo(525f, 520f)
                lineTo(360f, 520f)
                close()
            }
        }.build()

        return _SongListAdd!!
    }

@Suppress("ObjectPropertyName")
private var _SongListAdd: ImageVector? = null
