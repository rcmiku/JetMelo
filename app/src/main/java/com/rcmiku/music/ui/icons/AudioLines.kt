package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AudioLines: ImageVector
    get() {
        if (_AudioLines != null) {
            return _AudioLines!!
        }
        _AudioLines = ImageVector.Builder(
            name = "AudioLines",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(2f, 10f)
                verticalLineToRelative(3f)
                moveToRelative(4f, -7f)
                verticalLineToRelative(11f)
                moveToRelative(4f, -14f)
                verticalLineToRelative(18f)
                moveToRelative(4f, -13f)
                verticalLineToRelative(7f)
                moveToRelative(4f, -10f)
                verticalLineToRelative(13f)
                moveToRelative(4f, -8f)
                verticalLineToRelative(3f)
            }
        }.build()

        return _AudioLines!!
    }

@Suppress("ObjectPropertyName")
private var _AudioLines: ImageVector? = null

