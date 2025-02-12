package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val TablerCoin: ImageVector
    get() {
        if (_TablerCoin != null) {
            return _TablerCoin!!
        }
        _TablerCoin = ImageVector.Builder(
            name = "TablerCoin",
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
                moveTo(3f, 12f)
                arcToRelative(9f, 9f, 0f, isMoreThanHalf = true, isPositiveArc = false, 18f, 0f)
                arcToRelative(9f, 9f, 0f, isMoreThanHalf = true, isPositiveArc = false, -18f, 0f)
            }
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(14.8f, 9f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 13f, 8f)
                horizontalLineToRelative(-2f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = true, isPositiveArc = false, 0f, 4f)
                horizontalLineToRelative(2f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = true, isPositiveArc = true, 0f, 4f)
                horizontalLineToRelative(-2f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.8f, -1f)
                moveTo(12f, 7f)
                verticalLineToRelative(10f)
            }
        }.build()

        return _TablerCoin!!
    }

@Suppress("ObjectPropertyName")
private var _TablerCoin: ImageVector? = null
