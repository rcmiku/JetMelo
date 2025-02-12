package com.rcmiku.music.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaMetadata
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rcmiku.music.R
import com.rcmiku.music.constants.GridThumbnailHeight
import com.rcmiku.music.constants.ListItemHeight
import com.rcmiku.music.constants.ListThumbnailSize
import com.rcmiku.music.constants.ThumbnailCornerRadius
import com.rcmiku.music.ui.icons.FavoriteFill
import com.rcmiku.music.ui.icons.Payment
import com.rcmiku.music.ui.icons.Vip
import com.rcmiku.music.utils.formatPlayCount
import com.rcmiku.music.utils.formatTimestamp
import com.rcmiku.ncmapi.model.Album
import com.rcmiku.ncmapi.model.CloudSong
import com.rcmiku.ncmapi.model.Playlist
import com.rcmiku.ncmapi.model.PlaylistV1
import com.rcmiku.ncmapi.model.Radio
import com.rcmiku.ncmapi.model.SearchArtist
import com.rcmiku.ncmapi.model.Song
import com.rcmiku.ncmapi.model.SubAlbum
import com.rcmiku.ncmapi.model.VoiceBaseInfo

const val ActiveBoxAlpha = 0.6f

@Composable
inline fun ListItem(
    modifier: Modifier = Modifier,
    title: String,
    noinline subtitle: (@Composable RowScope.() -> Unit)? = null,
    thumbnailContent: @Composable () -> Unit,
    trailingContent: @Composable RowScope.() -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(ListItemHeight)
            .padding(horizontal = 6.dp),
    ) {
        Box(
            modifier = Modifier.padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            thumbnailContent()
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 6.dp)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (subtitle != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    subtitle()
                }
            }
        }

        trailingContent()
    }
}

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String?,
    badges: @Composable RowScope.() -> Unit = {},
    thumbnailContent: @Composable () -> Unit,
    trailingContent: @Composable RowScope.() -> Unit = {},
) = ListItem(
    title = title,
    subtitle = {
        badges()
        if (!subtitle.isNullOrEmpty()) {
            Text(
                text = subtitle,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    },
    thumbnailContent = thumbnailContent,
    trailingContent = trailingContent,
    modifier = modifier
)

@Composable
fun GridItem(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)?,
    badges: @Composable RowScope.() -> Unit = {},
    thumbnailContent: @Composable BoxWithConstraintsScope.() -> Unit,
    thumbnailRatio: Float = 1f,
    fillMaxWidth: Boolean = false,
) {
    Column(
        modifier = if (fillMaxWidth) {
            modifier
                .padding(12.dp)
                .fillMaxWidth()
        } else {
            modifier
                .padding(12.dp)
                .width(GridThumbnailHeight * thumbnailRatio)
        }
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = if (fillMaxWidth) {
                Modifier.fillMaxWidth()
            } else {
                Modifier.height(GridThumbnailHeight)
            }
                .aspectRatio(thumbnailRatio)
        ) {
            thumbnailContent()
        }

        Spacer(modifier = Modifier.height(6.dp))

        title()

        Row(verticalAlignment = Alignment.CenterVertically) {
            badges()

            subtitle?.invoke()
        }
    }
}

@Composable
fun GridItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    thumbnailContent: @Composable (BoxWithConstraintsScope.() -> Unit),
    thumbnailRatio: Float = 1f,
    fillMaxWidth: Boolean = false,
    textAlign: TextAlign = TextAlign.Start,
    maxLine: Int = 1
) = GridItem(
    modifier = modifier,
    title = {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            maxLines = maxLine,
            textAlign = textAlign,
            modifier = Modifier
                .basicMarquee()
                .fillMaxWidth()
        )
    },
    subtitle = {
        subtitle?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
            )
        }
    },
    thumbnailContent = thumbnailContent,
    thumbnailRatio = thumbnailRatio,
    fillMaxWidth = fillMaxWidth
)

@Composable
fun ArtistListItem(
    artist: SearchArtist,
    modifier: Modifier = Modifier,
    trailingContent: @Composable RowScope.() -> Unit = {},
) = ListItem(
    title = artist.name,
    thumbnailContent = {
        AsyncImage(
            model = artist.picUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(ListThumbnailSize)
        )
    },
    trailingContent = trailingContent,
    modifier = modifier
)

@Composable
fun AlbumListItem(
    album: Album,
    modifier: Modifier = Modifier,
    trailingContent: @Composable RowScope.() -> Unit = {},
) = ListItem(
    title = album.name,
    subtitle = stringResource(
        R.string.album_size,
        album.size
    ) + " " + album.artists.joinToString("/") { it.name },
    thumbnailContent = {
        AsyncImage(
            model = album.picUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(ThumbnailCornerRadius))
                .size(ListThumbnailSize)
        )
    },
    trailingContent = trailingContent,
    modifier = modifier
)

@Composable
fun AlbumListItem(
    album: SubAlbum,
    modifier: Modifier = Modifier,
    trailingContent: @Composable RowScope.() -> Unit = {},
    thumbnailContent: @Composable () -> Unit
) = ListItem(
    title = album.name,
    subtitle = stringResource(
        R.string.album_size,
        album.size
    ) + " " + album.artists.joinToString("/") { it.name },
    thumbnailContent = thumbnailContent,
    trailingContent = trailingContent,
    modifier = modifier
)

@Composable
fun VoiceListItem(
    voice: VoiceBaseInfo,
    modifier: Modifier = Modifier,
    trailingContent: @Composable RowScope.() -> Unit = {},
) = ListItem(
    title = voice.name,
    subtitle = stringResource(R.string.voice_count, voice.programCount),
    thumbnailContent = {
        AsyncImage(
            model = voice.picUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(ThumbnailCornerRadius))
                .size(ListThumbnailSize)
        )
    },
    trailingContent = trailingContent,
    modifier = modifier
)

@Composable
fun PlaylistListItem(
    playlist: Playlist,
    modifier: Modifier = Modifier,
    trailingContent: @Composable RowScope.() -> Unit = {},
) = ListItem(
    title = playlist.name,
    subtitle = playlist.playCount?.let { formatPlayCount(it) }?.let {
        stringResource(
            R.string.total_play_count,
            it
        )
    },
    thumbnailContent = {
        AsyncImage(
            model = playlist.picUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(ThumbnailCornerRadius))
                .size(ListThumbnailSize)
        )
    },
    trailingContent = trailingContent,
    modifier = modifier
)


@Composable
fun PlaylistV1ListItem(
    playlist: PlaylistV1,
    modifier: Modifier = Modifier,
    trailingContent: @Composable RowScope.() -> Unit = {},
) = ListItem(
    title = playlist.name,
    subtitle = stringResource(
        R.string.track_count,
        playlist.trackCount
    ),
    thumbnailContent = {
        AsyncImage(
            model = playlist.coverImgUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(ThumbnailCornerRadius))
                .size(ListThumbnailSize)
        )
    },
    trailingContent = trailingContent,
    modifier = modifier
)

@Composable
fun PlaylistGridItem(
    playlist: Playlist,
    modifier: Modifier = Modifier,
    thumbnailContent: @Composable (BoxWithConstraintsScope.() -> Unit)
) = GridItem(
    modifier = modifier,
    title = playlist.name,
    thumbnailContent = thumbnailContent,
    fillMaxWidth = true
)

@Composable
fun PlaylistGridItem(
    playlist: Playlist,
    modifier: Modifier = Modifier,
    thumbnailContent: @Composable (BoxWithConstraintsScope.() -> Unit),
    maxLine: Int = 1
) = GridItem(
    modifier = modifier,
    title = playlist.name,
    thumbnailContent = thumbnailContent,
    fillMaxWidth = true,
    textAlign = TextAlign.Center,
    maxLine = maxLine
)


@Composable
fun NewestAlbumItem(
    album: Album,
    modifier: Modifier = Modifier,
    thumbnailContent: @Composable (BoxWithConstraintsScope.() -> Unit)
) = GridItem(
    modifier = modifier,
    title = album.name,
    subtitle = album.artist.name,
    thumbnailContent = thumbnailContent
)

@Composable
fun SongListItem(
    song: Song,
    modifier: Modifier = Modifier,
    albumIndex: Int? = null,
    songIndex: Int? = null,
    showLikedIcon: Boolean = false,
    badges: @Composable RowScope.() -> Unit = {
        if (showLikedIcon) {
            Icon(
                imageVector = FavoriteFill,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .size(18.dp)
                    .padding(end = 2.dp)
            )
        }
        if (song.fee == 1) {
            Icon(
                imageVector = Vip,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .size(18.dp)
                    .padding(end = 2.dp)
            )
        }
        if (song.fee == 4) {
            Icon(
                imageVector = Payment,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(4.dp))
        }
    },
    isActive: Boolean = false,
    isPlaying: Boolean = false,
    trailingContent: @Composable RowScope.() -> Unit = {},
) = ListItem(
    title = if (songIndex != null) "$songIndex. " + song.name else song.name,
    subtitle = song.ar.joinToString("/") { it.name },
    badges = badges,
    thumbnailContent = {
        ItemThumbnail(
            thumbnailUrl = song.al.picUrl,
            cacheKey = song.al.pic.toString(),
            albumIndex = albumIndex,
            isActive = isActive,
            isPlaying = isPlaying,
            shape = RoundedCornerShape(ThumbnailCornerRadius),
            modifier = Modifier.size(ListThumbnailSize)
        )
    },
    trailingContent = trailingContent,
    modifier = modifier
)


@Composable
fun RadioListItem(
    radio: Radio,
    modifier: Modifier = Modifier,
    albumIndex: Int? = null,
    radioIndex: Int? = null,
    badges: @Composable RowScope.() -> Unit = {},
    isActive: Boolean = false,
    isPlaying: Boolean = false,
    trailingContent: @Composable RowScope.() -> Unit = {},
) = ListItem(
    title = if (radioIndex != null) "$radioIndex. " + radio.mainSong.name else radio.mainSong.name,
    subtitle = formatTimestamp(radio.createTime) + " " + radio.mainSong.artists.joinToString("/") { it.name },
    badges = badges,
    thumbnailContent = {
        ItemThumbnail(
            thumbnailUrl = radio.coverUrl,
            cacheKey = radio.coverUrl,
            albumIndex = albumIndex,
            isActive = isActive,
            isPlaying = isPlaying,
            shape = RoundedCornerShape(ThumbnailCornerRadius),
            modifier = Modifier.size(ListThumbnailSize)
        )
    },
    trailingContent = trailingContent,
    modifier = modifier
)


@Composable
fun CloudSongListItem(
    cloudSong: CloudSong,
    modifier: Modifier = Modifier,
    albumIndex: Int? = null,
    songIndex: Int? = null,
    badges: @Composable RowScope.() -> Unit = {
    },
    isActive: Boolean = false,
    isPlaying: Boolean = false,
    trailingContent: @Composable RowScope.() -> Unit = {},
) = ListItem(
    title = if (songIndex != null) "$songIndex. " + cloudSong.simpleSong.name else cloudSong.simpleSong.name,
    subtitle = cloudSong.artist,
    badges = badges,
    thumbnailContent = {
        ItemThumbnail(
            thumbnailUrl = cloudSong.simpleSong.al?.picUrl,
            cacheKey = cloudSong.simpleSong.al?.pic.toString(),
            albumIndex = albumIndex,
            isActive = isActive,
            isPlaying = isPlaying,
            shape = RoundedCornerShape(ThumbnailCornerRadius),
            modifier = Modifier.size(ListThumbnailSize)
        )
    },
    trailingContent = trailingContent,
    modifier = modifier
)

@Composable
fun MediaItemListItem(
    mediaMetadata: MediaMetadata,
    modifier: Modifier = Modifier,
    albumIndex: Int? = null,
    badges: @Composable RowScope.() -> Unit = {

    },
    isActive: Boolean = false,
    isPlaying: Boolean = false,
    trailingContent: @Composable RowScope.() -> Unit = {},
) = ListItem(
    title = mediaMetadata.title.toString(),
    subtitle = mediaMetadata.artist.toString(),
    badges = badges,
    thumbnailContent = {
        ItemThumbnail(
            thumbnailUrl = mediaMetadata.artworkUri.toString(),
            cacheKey = mediaMetadata.artworkUri.toString(),
            albumIndex = albumIndex,
            isActive = isActive,
            isPlaying = isPlaying,
            shape = RoundedCornerShape(ThumbnailCornerRadius),
            modifier = Modifier.size(ListThumbnailSize)
        )
    },
    trailingContent = trailingContent,
    modifier = modifier
)


@Composable
fun ItemThumbnail(
    thumbnailUrl: String?,
    cacheKey: String?,
    isActive: Boolean,
    isPlaying: Boolean,
    shape: Shape,
    modifier: Modifier = Modifier,
    albumIndex: Int? = null,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        if (albumIndex != null) {
            AnimatedVisibility(
                visible = !isActive,
                enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
                exit = shrinkOut(shrinkTowards = Alignment.Center) + fadeOut()
            ) {
                Text(
                    text = albumIndex.toString(),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnailUrl)
                    .crossfade(true)
                    .placeholderMemoryCacheKey(cacheKey)
                    .memoryCacheKey(cacheKey)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(shape)
            )
        }

        PlayingIndicatorBox(
            isActive = isActive,
            playWhenReady = isPlaying,
            color = if (albumIndex != null) MaterialTheme.colorScheme.onBackground else Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (albumIndex != null) Color.Transparent else Color.Black.copy(alpha = ActiveBoxAlpha),
                    shape = shape
                )
        )
    }
}