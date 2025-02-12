package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LibraryAddCheck: ImageVector
    get() {
        if (_LibraryAddCheck != null) {
            return _LibraryAddCheck!!
        }
        _LibraryAddCheck = ImageVector.Builder(
            name = "LibraryAddCheck",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveToRelative(508f, 562f)
                lineToRelative(226f, -226f)
                lineToRelative(-56f, -58f)
                lineToRelative(-170f, 170f)
                lineToRelative(-86f, -84f)
                lineToRelative(-56f, 56f)
                lineToRelative(142f, 142f)
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

        return _LibraryAddCheck!!
    }

@Suppress("ObjectPropertyName")
private var _LibraryAddCheck: ImageVector? = null
