package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Money: ImageVector
    get() {
        if (_Money != null) {
            return _Money!!
        }
        _Money = ImageVector.Builder(
            name = "Money",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(3.005f, 3.003f)
                horizontalLineToRelative(18f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 1f)
                verticalLineToRelative(16f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, 1f)
                horizontalLineToRelative(-18f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, -1f)
                verticalLineToRelative(-16f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, -1f)
                moveToRelative(1f, 2f)
                verticalLineToRelative(14f)
                horizontalLineToRelative(16f)
                verticalLineToRelative(-14f)
                close()
                moveTo(13.005f, 13.003f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-3f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(-3f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(-1f)
                horizontalLineToRelative(-3f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(2.586f)
                lineTo(8.469f, 7.88f)
                lineToRelative(1.415f, -1.414f)
                lineToRelative(2.12f, 2.122f)
                lineToRelative(2.122f, -2.122f)
                lineTo(15.54f, 7.88f)
                lineToRelative(-2.12f, 2.122f)
                horizontalLineToRelative(2.585f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(-3f)
                close()
            }
        }.build()

        return _Money!!
    }

@Suppress("ObjectPropertyName")
private var _Money: ImageVector? = null
