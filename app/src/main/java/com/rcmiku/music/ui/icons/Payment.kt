package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Payment: ImageVector
    get() {
        if (_Payment != null) {
            return _Payment!!
        }
        _Payment = ImageVector.Builder(
            name = "Payment",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(15.75f, 14.5f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0f,
                    1.5f
                )
                horizontalLineToRelative(2.5f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0f,
                    -1.5f
                )
                close()
                moveTo(2f, 8.25f)
                arcTo(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5.25f, 5f)
                horizontalLineToRelative(13.5f)
                arcTo(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 22f, 8.25f)
                verticalLineToRelative(7.5f)
                arcTo(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 18.75f, 19f)
                horizontalLineTo(5.25f)
                arcTo(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 15.75f)
                close()
                moveTo(20.5f, 9.5f)
                verticalLineTo(8.25f)
                arcToRelative(
                    1.75f,
                    1.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -1.75f,
                    -1.75f
                )
                horizontalLineTo(5.25f)
                arcTo(1.75f, 1.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3.5f, 8.25f)
                verticalLineTo(9.5f)
                close()
                moveTo(3.5f, 11f)
                verticalLineToRelative(4.75f)
                curveToRelative(0f, 0.966f, 0.784f, 1.75f, 1.75f, 1.75f)
                horizontalLineToRelative(13.5f)
                arcToRelative(
                    1.75f,
                    1.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    1.75f,
                    -1.75f
                )
                verticalLineTo(11f)
                close()
            }
        }.build()

        return _Payment!!
    }

@Suppress("ObjectPropertyName")
private var _Payment: ImageVector? = null
