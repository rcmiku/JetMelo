package com.rcmiku.music.ui.screen

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rcmiku.music.LocalPlayerController
import com.rcmiku.music.LocalPlayerState
import com.rcmiku.music.R
import com.rcmiku.music.data.favoriteSongIdsDatastore
import com.rcmiku.music.extensions.playMediaAt
import com.rcmiku.music.extensions.playMediaAtId
import com.rcmiku.music.extensions.setPlaylist
import com.rcmiku.music.ui.components.PlaylistThumbnailImage
import com.rcmiku.music.ui.components.SongListItem
import com.rcmiku.music.ui.components.SongMenuBottomSheet
import com.rcmiku.music.ui.icons.LibraryAdd
import com.rcmiku.music.ui.icons.LibraryAddCheck
import com.rcmiku.music.utils.formatPlayCount
import com.rcmiku.music.utils.formatTimestamp
import com.rcmiku.music.viewModel.PlaylistScreenViewModel
import com.rcmiku.ncmapi.model.Song
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun PlaylistScreen(
    navController: NavHostController,
    playlistScreenViewModel: PlaylistScreenViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val playlistDetailState by playlistScreenViewModel.playlistDetail.collectAsState()
    val listState = rememberLazyListState()
    val showPlaylistTitle by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    var playlistTitle by remember { mutableStateOf("") }
    val mediaController = LocalPlayerController.current.controller
    val playerState = LocalPlayerState.current
    val isPlaying = playerState?.isPlaying == true
    val currentMediaId = playerState?.currentMediaItem?.mediaId?.toLongOrNull()
    val playlistInfoState by playlistScreenViewModel.playlistInfo.collectAsState()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var selectSong by remember { mutableStateOf<Song?>(null) }
    val context = LocalContext.current
    val songIds by context.favoriteSongIdsDatastore.data.map { it.songIdsList }
        .collectAsState(emptyList())

    with(sharedTransitionScope) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            if (showPlaylistTitle) playlistTitle else stringResource(R.string.playlist),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                )
            }
        ) { padding ->
            playlistDetailState?.let {
                LazyColumn(
                    contentPadding = padding, state = listState,
                ) {
                    playlistTitle = it.playlist.name
                    item {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            PlaylistThumbnailImage(
                                url = it.playlist.coverImgUrl,
                                modifier = Modifier.sharedElement(
                                    sharedTransitionScope.rememberSharedContentState(key = it.playlist.id),
                                    animatedVisibilityScope = animatedContentScope,
                                    placeHolderSize = { contentSize: IntSize, animatedSize: IntSize ->
                                        IntSize(contentSize.width, animatedSize.height)
                                    },
                                    boundsTransform = AlbumArtBoundsTransform,
                                )
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = it.playlist.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = stringResource(
                                    R.string.total_play_count,
                                    formatPlayCount(it.playlist.playCount)
                                ) + " " + formatTimestamp(
                                    it.playlist.trackUpdateTime
                                ),
                                style = MaterialTheme.typography.labelMedium,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Spacer(Modifier.height(6.dp))
                            it.playlist.description?.let { description ->
                                Text(
                                    text = description,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.labelMedium,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 4,
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                )
                            }

                            Spacer(Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                OutlinedButton(
                                    onClick = {
                                        playlistInfoState?.subscribed?.let {
                                            playlistScreenViewModel.playlistSub(isSub = it)
                                        }
                                    },
                                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = if (playlistInfoState?.subscribed == true) LibraryAddCheck else LibraryAdd,
                                        contentDescription = null,
                                        modifier = Modifier.size(ButtonDefaults.IconSize)
                                    )
                                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                    Text(
                                        text = if (playlistInfoState?.subscribed == true) stringResource(
                                            R.string.library_add_check
                                        ) else stringResource(R.string.library_add)
                                    )
                                }
                                Button(
                                    onClick = {
                                        mediaController?.setPlaylist(it.playlist.tracks)
                                        mediaController?.playMediaAt()
                                    },
                                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        Icons.Outlined.PlayArrow,
                                        contentDescription = null,
                                        modifier = Modifier.size(ButtonDefaults.IconSize)
                                    )
                                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                    Text(
                                        text = stringResource(R.string.play)
                                    )
                                }
                            }
                        }

                    }

                    itemsIndexed(it.playlist.tracks) { index, song ->
                        SongListItem(
                            song = song,
                            isPlaying = isPlaying,
                            showLikedIcon = song.id in songIds,
                            isActive = currentMediaId == song.id,
                            songIndex = index + 1,
                            modifier = Modifier.clickable {
                                mediaController?.setPlaylist(it.playlist.tracks)
                                mediaController?.playMediaAtId(song.id)
                            },
                            trailingContent = {
                                IconButton(onClick = {
                                    selectSong = song
                                    openBottomSheet = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = stringResource(R.string.more)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    SongMenuBottomSheet(
        navController = navController,
        song = selectSong,
        onDismiss = { openBottomSheet = false },
        openBottomSheet = openBottomSheet
    )
}