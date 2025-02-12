package com.rcmiku.music.constants

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.PathEasing
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

val ListItemHeight = 64.dp
val ListThumbnailSize = 48.dp
val ThumbnailCornerRadius = 8.dp
val GridThumbnailHeight = 128.dp
val MediaItemHeight = 72.dp
val AlbumThumbnailSize = 144.dp
val MiniPlayerHeight = 64.dp
val PlaylistThumbnailSize = 200.dp
val SettingItemHeight = 64.dp
val SettingItemCorner = 16.dp
val SettingItemSubCorner = 4.dp

const val DURATION = 600
const val DURATION_ENTER = 400
const val DURATION_EXIT = 200
const val DURATION_EXIT_SHORT = 100

private val emphasizedPath = Path().apply {
    moveTo(0f, 0f)
    cubicTo(0.05f, 0f, 0.133333f, 0.06f, 0.166666f, 0.4f)
    cubicTo(0.208333f, 0.82f, 0.25f, 1f, 1f, 1f)
}

val EmphasizedEasing: Easing = PathEasing(emphasizedPath)
val EmphasizedDecelerateEasing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1f)
val EmphasizedAccelerateEasing = CubicBezierEasing(0.3f, 0f, 0.8f, 0.15f)
