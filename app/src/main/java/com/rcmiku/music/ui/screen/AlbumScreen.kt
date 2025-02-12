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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.rcmiku.music.LocalPlayerController
import com.rcmiku.music.LocalPlayerState
import com.rcmiku.music.R
import com.rcmiku.music.constants.ThumbnailCornerRadius
import com.rcmiku.music.data.favoriteSongIdsDatastore
import com.rcmiku.music.extensions.playMediaAt
import com.rcmiku.music.extensions.playMediaAtId
import com.rcmiku.music.extensions.setPlaylist
import com.rcmiku.music.ui.components.SongListItem
import com.rcmiku.music.ui.components.SongMenuBottomSheet
import com.rcmiku.music.ui.icons.LibraryAdd
import com.rcmiku.music.ui.icons.LibraryAddCheck
import com.rcmiku.music.ui.navigation.ArtistNav
import com.rcmiku.music.utils.formatTimestamp
import com.rcmiku.music.viewModel.AlbumScreenViewModel
import com.rcmiku.ncmapi.model.Song
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun AlbumScreen(
    navController: NavHostController,
    albumScreenViewModel: AlbumScreenViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val albumDetailState by albumScreenViewModel.albumDetail.collectAsState()
    val listState = rememberLazyListState()
    val showAlbumTitle by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    var playlistTitle by remember { mutableStateOf("") }
    val mediaController = LocalPlayerController.current.controller
    val playerState = LocalPlayerState.current
    val isPlaying = playerState?.isPlaying == true
    val currentMediaId = playerState?.currentMediaItem?.mediaId?.toLongOrNull()
    val albumInfoState by albumScreenViewModel.albumInfo.collectAsState()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var selectSong by remember { mutableStateOf<Song?>(null) }
    val context = LocalContext.current
    val songIds by context.favoriteSongIdsDatastore.data.map { it.songIdsList }
        .collectAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (showAlbumTitle) playlistTitle else stringResource(R.string.album),
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
        LazyColumn(contentPadding = padding, state = listState) {
            albumDetailState?.onSuccess {
                playlistTitle = it.album.name
                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        with(sharedTransitionScope) {
                            AsyncImage(
                                model = it.album.picUrl,
                                contentDescription = it.album.name,
                                contentScale = ContentScale.Crop,
                                filterQuality = FilterQuality.High,
                                modifier = Modifier
                                    .sharedElement(
                                        sharedTransitionScope.rememberSharedContentState(key = it.album.id),
                                        animatedVisibilityScope = animatedContentScope
                                    )
                                    .size(200.dp)
                                    .clip(RoundedCornerShape(ThumbnailCornerRadius))
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = it.album.name,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Text(
                            text = it.album.artist.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(ArtistNav(artistId = it.album.artist.id))
                                }
                                .padding(horizontal = 10.dp)
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = formatTimestamp(it.album.publishTime),
                            style = MaterialTheme.typography.labelMedium,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = {
                                    albumInfoState?.isSub?.let { isSub ->
                                        albumScreenViewModel.albumSub(isSub = isSub)
                                    }
                                },
                                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = if (albumInfoState?.isSub == true) LibraryAddCheck else LibraryAdd,
                                    contentDescription = null,
                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                )
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(
                                    text = if (albumInfoState?.isSub == true) stringResource(R.string.library_add_check) else stringResource(
                                        R.string.library_add
                                    )
                                )
                            }
                            Button(
                                onClick = {
                                    mediaController?.setPlaylist(it.songs)
                                    mediaController?.playMediaAt()
                                },
                                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.PlayArrow,
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

                itemsIndexed(it.songs) { index, song ->
                    SongListItem(
                        isPlaying = isPlaying,
                        isActive = currentMediaId == song.id,
                        showLikedIcon = song.id in songIds,
                        song = song,
                        albumIndex = index + 1,
                        modifier = Modifier.clickable {
                            mediaController?.setPlaylist(it.songs)
                            mediaController?.playMediaAtId(song.id)
                        }, trailingContent = {
                            IconButton(onClick = {
                                selectSong = song
                                openBottomSheet = true
                            }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = stringResource(R.string.more)
                                )
                            }
                        })
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