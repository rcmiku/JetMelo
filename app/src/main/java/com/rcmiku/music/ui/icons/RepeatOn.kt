package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val RepeatOn: ImageVector
    get() {
        if (_RepeatOn != null) {
            return _RepeatOn!!
        }
        _RepeatOn = ImageVector.Builder(
            name = "RepeatOn",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFF5F6368))) {
                moveTo(120f, 920f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(40f, 840f)
                verticalLineToRelative(-720f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(120f, 40f)
                horizontalLineToRelative(720f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(920f, 120f)
                verticalLineToRelative(720f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(840f, 920f)
                lineTo(120f, 920f)
                close()
                moveTo(280f, 880f)
                lineTo(336f, 822f)
                lineTo(274f, 760f)
                horizontalLineToRelative(486f)
                verticalLineToRelative(-240f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(160f)
                lineTo(274f, 680f)
                lineToRelative(62f, -62f)
                lineToRelative(-56f, -58f)
                lineToRelative(-160f, 160f)
                lineTo(280f, 880f)
                close()
                moveTo(200f, 440f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(-160f)
                horizontalLineToRelative(406f)
                lineToRelative(-62f, 62f)
                lineToRelative(56f, 58f)
                lineToRelative(160f, -160f)
                lineToRelative(-160f, -160f)
                lineToRelative(-56f, 58f)
                lineToRelative(62f, 62f)
                lineTo(200f, 200f)
                verticalLineToRelative(240f)
                close()
            }
        }.build()

        return _RepeatOn!!
    }

@Suppress("ObjectPropertyName")
private var _RepeatOn: ImageVector? = null
