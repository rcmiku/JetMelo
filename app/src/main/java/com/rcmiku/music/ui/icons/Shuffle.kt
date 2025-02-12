package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Shuffle: ImageVector
    get() {
        if (_Shuffle != null) {
            return _Shuffle!!
        }
        _Shuffle = ImageVector.Builder(
            name = "Shuffle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF5F6368))) {
                moveTo(560f, 800f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(104f)
                lineTo(537f, 593f)
                lineToRelative(57f, -57f)
                lineToRelative(126f, 126f)
                verticalLineToRelative(-102f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(240f)
                lineTo(560f, 800f)
                close()
                moveTo(216f, 800f)
                lineTo(160f, 744f)
                lineTo(664f, 240f)
                lineTo(560f, 240f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(240f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(-104f)
                lineTo(216f, 800f)
                close()
                moveTo(367f, 423f)
                lineTo(160f, 216f)
                lineToRelative(56f, -56f)
                lineToRelative(207f, 207f)
                lineToRelative(-56f, 56f)
                close()
            }
        }.build()

        return _Shuffle!!
    }

@Suppress("ObjectPropertyName")
private var _Shuffle: ImageVector? = null
