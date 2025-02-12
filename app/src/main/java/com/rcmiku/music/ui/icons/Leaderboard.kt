package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Leaderboard: ImageVector
    get() {
        if (_Leaderboard != null) {
            return _Leaderboard!!
        }
        _Leaderboard = ImageVector.Builder(
            name = "Leaderboard",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(160f, 760f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(-320f)
                lineTo(160f, 440f)
                verticalLineToRelative(320f)
                close()
                moveTo(400f, 760f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(-560f)
                lineTo(400f, 200f)
                verticalLineToRelative(560f)
                close()
                moveTo(640f, 760f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(-240f)
                lineTo(640f, 520f)
                verticalLineToRelative(240f)
                close()
                moveTo(80f, 840f)
                verticalLineToRelative(-480f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(-240f)
                horizontalLineToRelative(320f)
                verticalLineToRelative(320f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(400f)
                lineTo(80f, 840f)
                close()
            }
        }.build()

        return _Leaderboard!!
    }

@Suppress("ObjectPropertyName")
private var _Leaderboard: ImageVector? = null
