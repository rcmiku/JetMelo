package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Wallet: ImageVector
    get() {
        if (_Wallet != null) {
            return _Wallet!!
        }
        _Wallet = ImageVector.Builder(
            name = "Wallet",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(14.356f, 2.595f)
                arcToRelative(
                    0.25f,
                    0.25f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.361f,
                    -0.032f
                )
                lineToRelative(0.922f, 0.812f)
                lineTo(12.739f, 7f)
                horizontalLineToRelative(1.92f)
                lineToRelative(2.106f, -2.632f)
                lineToRelative(1.652f, 1.457f)
                arcToRelative(
                    0.25f,
                    0.25f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.026f,
                    0.348f
                )
                lineToRelative(-0.69f, 0.827f)
                horizontalLineToRelative(1.944f)
                arcToRelative(
                    1.75f,
                    1.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -0.288f,
                    -2.3f
                )
                lineToRelative(-3.7f, -3.263f)
                arcToRelative(
                    1.75f,
                    1.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -2.531f,
                    0.23f
                )
                lineTo(8.976f, 7f)
                horizontalLineToRelative(1.91f)
                close()
                moveTo(16.25f, 14f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0f,
                    1.5f
                )
                horizontalLineToRelative(2f)
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
                moveTo(4.5f, 7.25f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.75f,
                    -0.75f
                )
                horizontalLineToRelative(3.128f)
                lineTo(9.57f, 5f)
                horizontalLineTo(5.25f)
                arcTo(2.25f, 2.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3f, 7.25f)
                verticalLineToRelative(10.5f)
                arcTo(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 6.25f, 21f)
                horizontalLineToRelative(12f)
                arcToRelative(
                    3.25f,
                    3.25f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    3.25f,
                    -3.25f
                )
                verticalLineToRelative(-6.5f)
                arcTo(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = false, 18.25f, 8f)
                horizontalLineToRelative(-13f)
                arcToRelative(
                    0.75f,
                    0.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.75f,
                    -0.75f
                )
                moveToRelative(0f, 10.5f)
                verticalLineTo(9.372f)
                quadToRelative(0.354f, 0.126f, 0.75f, 0.128f)
                horizontalLineToRelative(13f)
                curveToRelative(0.966f, 0f, 1.75f, 0.784f, 1.75f, 1.75f)
                verticalLineToRelative(6.5f)
                arcToRelative(
                    1.75f,
                    1.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -1.75f,
                    1.75f
                )
                horizontalLineToRelative(-12f)
                arcToRelative(
                    1.75f,
                    1.75f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -1.75f,
                    -1.75f
                )
            }
        }.build()

        return _Wallet!!
    }

@Suppress("ObjectPropertyName")
private var _Wallet: ImageVector? = null
