package com.rcmiku.music.ui.screen

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rcmiku.music.LocalPlayerController
import com.rcmiku.music.LocalPlayerState
import com.rcmiku.music.R
import com.rcmiku.music.constants.AlbumThumbnailSize
import com.rcmiku.music.constants.DURATION_EXIT_SHORT
import com.rcmiku.music.constants.ListItemHeight
import com.rcmiku.music.constants.ncmCookieKey
import com.rcmiku.music.data.favoriteSongIdsDatastore
import com.rcmiku.music.extensions.playMediaAtId
import com.rcmiku.music.extensions.setPlaylist
import com.rcmiku.music.ui.components.GridThumbnailImage
import com.rcmiku.music.ui.components.NavigationTitle
import com.rcmiku.music.ui.components.PlaylistGridItem
import com.rcmiku.music.ui.components.SongListItem
import com.rcmiku.music.ui.components.SongMenuBottomSheet
import com.rcmiku.music.ui.components.TopBar
import com.rcmiku.music.ui.navigation.PlaylistNav
import com.rcmiku.music.utils.rememberPreference
import com.rcmiku.music.viewModel.HomeScreenViewModel
import com.rcmiku.ncmapi.model.Song
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val recommendSongsState by homeScreenViewModel.recommendSongs.collectAsState()
    val recommendPlaylistState by homeScreenViewModel.recommendPlaylist.collectAsState()
    val personalizedPlaylistState by homeScreenViewModel.personalizedPlaylist.collectAsState()
    val gridState = rememberLazyGridState()
    val mediaController = LocalPlayerController.current.controller
    val playerState = LocalPlayerState.current
    val ncmCookie by rememberPreference(ncmCookieKey, "")
    val isPlaying = playerState?.isPlaying == true
    val currentMediaId = playerState?.currentMediaItem?.mediaId?.toLongOrNull()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var selectSong by remember { mutableStateOf<Song?>(null) }
    val context = LocalContext.current
    val state = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val songIds by context.favoriteSongIdsDatastore.data.map { it.songIdsList }
        .collectAsState(emptyList())
    val coroutineScope = rememberCoroutineScope()

    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            homeScreenViewModel.refresh()
            delay(1000)
            isRefreshing = false
        }
    }

    with(sharedTransitionScope) {

        Scaffold(
            topBar = {
                TopBar(navController = navController, titleRes = R.string.home)
            },
        ) { padding ->
            PullToRefreshBox(
                modifier = Modifier
                    .padding(top = padding.calculateTopPadding())
                    .fillMaxSize(),
                state = state,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = isRefreshing,
                        state = state
                    )
                }
            ) {
                LazyColumn {
                    item {
                        recommendSongsState?.onSuccess {
                            NavigationTitle(
                                title = stringResource(R.string.recommend_songs),
                                modifier = Modifier.animateItem()
                            )
                            LazyHorizontalGrid(
                                rows = GridCells.Fixed(4),
                                state = gridState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(ListItemHeight * 4)
                                    .animateItem(),
                                flingBehavior = rememberSnapFlingBehavior(
                                    gridState,
                                    snapPosition = SnapPosition.Start
                                )
                            ) {
                                itemsIndexed(it.data.dailySongs) { _, song ->
                                    SongListItem(
                                        isPlaying = isPlaying,
                                        isActive = currentMediaId == song.id,
                                        showLikedIcon = song.id in songIds,
                                        song = song,
                                        modifier = Modifier
                                            .clip(MaterialTheme.shapes.small)
                                            .width(340.dp)
                                            .clickable {
                                                mediaController?.setPlaylist(it.data.dailySongs)
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

                    item {
                        recommendPlaylistState?.onSuccess {
                            NavigationTitle(
                                title = stringResource(R.string.personalized_playlist),
                                modifier = Modifier.animateItem()
                            )
                            LazyRow {
                                items(it.recommend) { playlist ->
                                    PlaylistGridItem(
                                        playlist = playlist,
                                        modifier = Modifier
                                            .clip(MaterialTheme.shapes.small)
                                            .width(AlbumThumbnailSize)
                                            .clickable(
                                                onClick = {
                                                    navController.navigate(PlaylistNav(playlistId = playlist.id))
                                                }
                                            ),
                                        thumbnailContent = {
                                            GridThumbnailImage(
                                                url = playlist.picUrl,
                                                modifier = Modifier
                                                    .sharedElement(
                                                        sharedTransitionScope.rememberSharedContentState(
                                                            key = playlist.id
                                                        ),
                                                        animatedVisibilityScope = animatedContentScope,
                                                        renderInOverlayDuringTransition = false,
                                                        zIndexInOverlay = 10f
                                                    )
                                            )
                                        }
                                    )

                                }
                            }
                        }
                        personalizedPlaylistState?.onSuccess {
                            NavigationTitle(
                                title = stringResource(R.string.recommend_playlist),
                                modifier = Modifier.animateItem()
                            )
                            LazyRow {
                                items(it.result) { playlist ->

                                    PlaylistGridItem(
                                        playlist = playlist,
                                        modifier = Modifier
                                            .sharedBounds(
                                                sharedContentState = rememberSharedContentState(
                                                    key = playlist.name + playlist.id
                                                ),
                                                animatedVisibilityScope = animatedContentScope,
                                                placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize,
                                                boundsTransform = AlbumArtBoundsTransform,
                                                enter = fadeIn(
                                                    tweenEnter(delayMillis = DURATION_EXIT_SHORT)
                                                ),
                                                exit = fadeOut(
                                                    tweenExit(durationMillis = DURATION_EXIT_SHORT)
                                                )
                                            )
                                            .clip(MaterialTheme.shapes.small)
                                            .width(AlbumThumbnailSize)
                                            .clickable(
                                                onClick = {
                                                    navController.navigate(
                                                        PlaylistNav(
                                                            playlistId = playlist.id,
                                                            limit = playlist.trackCount
                                                        )
                                                    )
                                                }
                                            ),
                                        thumbnailContent = {
                                            GridThumbnailImage(
                                                url = playlist.picUrl,
                                                modifier = Modifier
                                                    .sharedElement(
                                                        sharedTransitionScope.rememberSharedContentState(
                                                            key = playlist.id
                                                        ),
                                                        animatedVisibilityScope = animatedContentScope
                                                    )
                                            )
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

    SongMenuBottomSheet(
        navController = navController,
        song = selectSong,
        onDismiss = { openBottomSheet = false },
        openBottomSheet = openBottomSheet
    )
}