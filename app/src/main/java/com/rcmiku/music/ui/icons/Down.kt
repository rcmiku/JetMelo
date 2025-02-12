package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Down: ImageVector
    get() {
        if (_Down != null) {
            return _Down!!
        }
        _Down = ImageVector.Builder(
            name = "Down",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF5F6368))) {
                moveTo(2f, 8.025f)
                lineTo(3.775f, 6.25f)
                lineTo(12f, 14.475f)
                lineTo(20.225f, 6.25f)
                lineTo(22f, 8.025f)
                lineTo(12f, 18.025f)
                lineTo(2f, 8.025f)
                close()
            }
        }.build()

        return _Down!!
    }

@Suppress("ObjectPropertyName")
private var _Down: ImageVector? = null
