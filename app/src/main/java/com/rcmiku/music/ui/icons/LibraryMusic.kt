package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LibraryMusic: ImageVector
    get() {
        if (_LibraryMusic != null) {
            return _LibraryMusic!!
        }
        _LibraryMusic = ImageVector.Builder(
            name = "LibraryMusic",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF5F6368))) {
                moveTo(500f, 600f)
                quadToRelative(42f, 0f, 71f, -29f)
                reflectiveQuadToRelative(29f, -71f)
                verticalLineToRelative(-220f)
                horizontalLineToRelative(120f)
                verticalLineToRelative(-80f)
                lineTo(560f, 200f)
                verticalLineToRelative(220f)
                quadToRelative(-13f, -10f, -28f, -15f)
                reflectiveQuadToRelative(-32f, -5f)
                quadToRelative(-42f, 0f, -71f, 29f)
                reflectiveQuadToRelative(-29f, 71f)
                quadToRelative(0f, 42f, 29f, 71f)
                reflectiveQuadToRelative(71f, 29f)
                close()
                moveTo(320f, 720f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(240f, 640f)
                verticalLineToRelative(-480f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(320f, 80f)
                horizontalLineToRelative(480f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(880f, 160f)
                verticalLineToRelative(480f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(800f, 720f)
                lineTo(320f, 720f)
                close()
                moveTo(320f, 640f)
                horizontalLineToRelative(480f)
                verticalLineToRelative(-480f)
                lineTo(320f, 160f)
                verticalLineToRelative(480f)
                close()
                moveTo(160f, 880f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(80f, 800f)
                verticalLineToRelative(-560f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(560f)
                horizontalLineToRelative(560f)
                verticalLineToRelative(80f)
                lineTo(160f, 880f)
                close()
                moveTo(320f, 160f)
                verticalLineToRelative(480f)
                verticalLineToRelative(-480f)
                close()
            }
        }.build()

        return _LibraryMusic!!
    }

@Suppress("ObjectPropertyName")
private var _LibraryMusic: ImageVector? = null
