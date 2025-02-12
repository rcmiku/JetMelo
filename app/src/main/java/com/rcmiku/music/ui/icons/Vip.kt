package com.rcmiku.music.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Vip: ImageVector
    get() {
        if (_Vip != null) {
            return _Vip!!
        }
        _Vip = ImageVector.Builder(
            name = "Vip",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 32f,
            viewportHeight = 32f
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveToRelative(7f, 13f)
                lineToRelative(3f, 6.5f)
                lineToRelative(3f, -6.5f)
                moveToRelative(3.5f, 0f)
                verticalLineToRelative(6.5f)
                moveToRelative(4f, -2.225f)
                horizontalLineToRelative(1.48f)
                curveToRelative(0.651f, 0f, 1.277f, -0.275f, 1.721f, -0.75f)
                arcToRelative(
                    2.34f,
                    2.34f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0.215f,
                    -2.932f
                )
                arcToRelative(
                    1.4f,
                    1.4f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -1.14f,
                    -0.593f
                )
                lineTo(20.5f, 13f)
                close()
                moveTo(20.5f, 17.275f)
                lineTo(20.5f, 19.5f)
                moveTo(5f, 7f)
                horizontalLineToRelative(22f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 2f)
                verticalLineToRelative(14f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2f, 2f)
                lineTo(5f, 25f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2f, -2f)
                lineTo(3f, 9f)
                arcToRelative(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, -2f)
            }
        }.build()

        return _Vip!!
    }

@Suppress("ObjectPropertyName")
private var _Vip: ImageVector? = null
