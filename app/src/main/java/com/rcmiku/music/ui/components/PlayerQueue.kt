package com.rcmiku.music.ui.components

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.HapticFeedbackConstantsCompat
import androidx.core.view.ViewCompat
import androidx.media3.common.MediaMetadata
import coil3.compose.AsyncImage
import com.rcmiku.music.LocalPlayerController
import com.rcmiku.music.LocalPlayerState
import com.rcmiku.music.R
import com.rcmiku.music.constants.MediaItemHeight
import com.rcmiku.music.constants.MediaSessionConstants
import com.rcmiku.music.extensions.currentMediaItems
import com.rcmiku.music.extensions.playMediaAt
import com.rcmiku.music.extensions.removeSong
import com.rcmiku.music.ui.icons.DragHandle
import com.rcmiku.music.ui.icons.Repeat
import com.rcmiku.music.ui.icons.RepeatOne
import com.rcmiku.music.ui.icons.Shuffle
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun PlayerQueue(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    mediaMetadata: MediaMetadata,
    onBackPressed: () -> Unit = {},
) {
    val playerState = LocalPlayerState.current
    val mediaController = LocalPlayerController.current.controller
    val isPlaying = playerState?.isPlaying == true
    val repeatMode = playerState?.repeatMode ?: 0
    val shuffleMode = playerState?.shuffleModeEnabled == true
    val currentMediaItems = playerState?.player?.currentMediaItems
    val currentMediaId = playerState?.player?.currentMediaItem?.mediaId
    val currentIndex = playerState?.player?.currentMediaItemIndex
    var cacheMediaItems by remember { mutableStateOf(currentMediaItems) }

    val repeatIcon = when (repeatMode) {
        0 -> Repeat
        1 -> RepeatOne
        else -> Repeat
    }

    BackHandler {
        onBackPressed()
    }

    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.fillMaxHeight(),
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clip(MaterialTheme.shapes.small)
                    .clickable { onBackPressed() }
            ) {
                AsyncImage(
                    model = mediaMetadata.artworkUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = imageModifier
                        .size(MediaItemHeight)
                        .clip(MaterialTheme.shapes.small)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .then(modifier)
                ) {
                    Text(
                        text = mediaMetadata.title.toString(),
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.basicMarquee()
                    )
                    Text(
                        text = mediaMetadata.artist.toString(),
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.basicMarquee(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 4.dp)
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.playing_list),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    currentMediaItems?.size?.let { size ->
                        Text(
                            text = stringResource(R.string.song_size, size),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }

                Row {
                    FilledTonalIconButton(
                        onClick = {
                            mediaController?.sendCustomCommand(
                                MediaSessionConstants.CommandToggleShuffle,
                                Bundle.EMPTY
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Shuffle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.alpha(if (!shuffleMode) 0.4f else 1f)
                        )
                    }

                    FilledTonalIconButton(
                        onClick = {
                            val newMode = when (repeatMode) {
                                0 -> 2
                                1 -> 0
                                2 -> 1
                                else -> 0
                            }
                            mediaController?.repeatMode = newMode
                        }
                    ) {
                        Icon(
                            imageVector = repeatIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.alpha(if (repeatMode == 0) 0.4f else 1f)
                        )
                    }
                }

            }

            val view = LocalView.current
            val lazyListState = rememberLazyListState()
            var dragInfo by remember {
                mutableStateOf<Pair<Int, Int>?>(null)
            }

            LaunchedEffect(Unit) {
                if (currentIndex != null) {
                    lazyListState.scrollToItem(currentIndex)
                }
            }

            val reorderableLazyListState =
                rememberReorderableLazyListState(lazyListState) { from, to ->
                    cacheMediaItems = cacheMediaItems?.toMutableList()?.apply {
                        add(to.index, removeAt(from.index))
                        val currentDragInfo = dragInfo
                        dragInfo = if (currentDragInfo == null) {
                            from.index to to.index
                        } else {
                            currentDragInfo.first to to.index
                        }
                    }
                    ViewCompat.performHapticFeedback(
                        view,
                        HapticFeedbackConstantsCompat.SEGMENT_FREQUENT_TICK
                    )
                }

            LaunchedEffect(reorderableLazyListState.isAnyItemDragging) {
                if (!reorderableLazyListState.isAnyItemDragging) {
                    dragInfo?.let { (from, to) ->
                        mediaController?.moveMediaItem(from, to)
                        dragInfo = null
                    }
                }
            }

            LaunchedEffect(playerState) {
                cacheMediaItems = currentMediaItems
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                contentPadding = WindowInsets.navigationBars.asPaddingValues()
            ) {
                cacheMediaItems?.let { mediaItemList ->
                    itemsIndexed(mediaItemList, key = { _, mediaItem ->
                        mediaItem.mediaId
                    }) { index, mediaItem ->

                        val dismissState = rememberSwipeToDismissBoxState(
                            positionalThreshold = { totalDistance -> totalDistance },
                            confirmValueChange = { dismissValue ->
                                if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                    cacheMediaItems = cacheMediaItems?.toMutableList()?.apply {
                                        removeIf { it.mediaId == mediaItem.mediaId }
                                    }
                                    mediaController?.removeSong(mediaItem.mediaId)
                                }
                                true
                            }
                        )

                        ReorderableItem(
                            reorderableLazyListState,
                            key = mediaItem.mediaId,
                        ) { _ ->
                            SwipeToDismissBox(
                                state = dismissState,
                                enableDismissFromStartToEnd = false,
                                backgroundContent = {

                                }
                            ) {
                                MediaItemListItem(
                                    isPlaying = isPlaying,
                                    isActive = currentMediaId == mediaItem.mediaId,
                                    mediaMetadata = mediaItem.mediaMetadata, trailingContent = {
                                        IconButton(
                                            modifier = Modifier
                                                .draggableHandle(
                                                    onDragStarted = {
                                                        ViewCompat.performHapticFeedback(
                                                            view,
                                                            HapticFeedbackConstantsCompat.GESTURE_START
                                                        )
                                                    },
                                                    onDragStopped = {
                                                        ViewCompat.performHapticFeedback(
                                                            view,
                                                            HapticFeedbackConstantsCompat.GESTURE_END
                                                        )
                                                    }
                                                )
                                                .padding(end = 6.dp),
                                            onClick = {},
                                        ) {
                                            Icon(DragHandle, contentDescription = null)
                                        }
                                    },
                                    modifier = Modifier.clickable {
                                        mediaController?.playMediaAt(index)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}