package com.rcmiku.music.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.rcmiku.music.constants.GridThumbnailHeight
import com.rcmiku.music.constants.ListThumbnailSize
import com.rcmiku.music.constants.PlaylistThumbnailSize
import com.rcmiku.music.constants.ThumbnailCornerRadius

@Composable
fun ListThumbnailImage(url: Any?, modifier: Modifier = Modifier) =
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(ThumbnailCornerRadius))
            .size(ListThumbnailSize)
    )

@Composable
fun GridThumbnailImage(url: Any?, modifier: Modifier = Modifier) =
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(ThumbnailCornerRadius))
            .size(GridThumbnailHeight)
    )

@Composable
fun PlaylistThumbnailImage(url: Any?, modifier: Modifier = Modifier) =
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(ThumbnailCornerRadius))
            .size(PlaylistThumbnailSize)
    )

@Composable
fun RadioThumbnailImage(url: Any?, modifier: Modifier = Modifier) =
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .size(PlaylistThumbnailSize)
    )