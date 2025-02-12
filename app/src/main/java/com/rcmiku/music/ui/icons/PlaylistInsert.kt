package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val PlaylistInsert: ImageVector
    get() {
        if (_PlaylistInsert != null) {
            return _PlaylistInsert!!
        }
        _PlaylistInsert = ImageVector.Builder(
            name = "PlaylistInsert",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(3f, 16f)
                verticalLineTo(14f)
                horizontalLineTo(10f)
                verticalLineTo(16f)
                horizontalLineTo(3f)
                close()
                moveTo(3f, 12f)
                verticalLineTo(10f)
                horizontalLineTo(14f)
                verticalLineTo(12f)
                horizontalLineTo(3f)
                close()
                moveTo(3f, 8f)
                verticalLineTo(6f)
                horizontalLineTo(14f)
                verticalLineTo(8f)
                horizontalLineTo(3f)
                close()
            }
            path(fill = SolidColor(Color(0xFF1F1F1F))) {
                moveTo(18.5f, 22f)
                lineTo(14f, 17.5f)
                verticalLineTo(21f)
                horizontalLineTo(12f)
                verticalLineTo(14f)
                horizontalLineTo(19f)
                verticalLineTo(16f)
                horizontalLineTo(15.5f)
                lineTo(20f, 20.5f)
                lineTo(18.5f, 22f)
                close()
            }
        }.build()

        return _PlaylistInsert!!
    }

@Suppress("ObjectPropertyName")
private var _PlaylistInsert: ImageVector? = null
