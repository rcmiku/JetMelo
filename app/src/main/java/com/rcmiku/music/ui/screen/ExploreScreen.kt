package com.rcmiku.music.ui.screen

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.rcmiku.music.R
import com.rcmiku.music.constants.ThumbnailCornerRadius
import com.rcmiku.music.ui.components.NavigationTitle
import com.rcmiku.music.ui.components.NewestAlbumItem
import com.rcmiku.music.ui.components.TopBar
import com.rcmiku.music.ui.components.TopListButton
import com.rcmiku.music.ui.components.TopListHeight
import com.rcmiku.music.ui.navigation.AlbumNav
import com.rcmiku.music.ui.navigation.PlaylistNav
import com.rcmiku.music.ui.navigation.Screen
import com.rcmiku.music.viewModel.ExploreScreenViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ExploreScreen(
    navController: NavHostController,
    exploreScreenViewModel: ExploreScreenViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {

    val topListState by exploreScreenViewModel.topList.collectAsState()
    val newAlbumState by exploreScreenViewModel.newAlbum.collectAsState()
    val gridState = rememberLazyGridState()

    Scaffold(topBar = {
        TopBar(navController = navController, titleRes = R.string.explore)
    }) { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                topListState?.onSuccess {

                    NavigationTitle(
                        title = stringResource(R.string.top_list),
                        modifier = Modifier.animateItem(),
                        onClick = {
                            navController.navigate(Screen.TopList.route)
                        }
                    )

                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(4),
                        contentPadding = PaddingValues(6.dp),
                        state = gridState,
                        flingBehavior = rememberSnapFlingBehavior(
                            gridState,
                            snapPosition = SnapPosition.Start
                        ),
                        modifier = Modifier
                            .height((TopListHeight + 12.dp) * 4 + 12.dp)
                            .animateItem()
                    ) {
                        items(it.list) { topList ->
                            TopListButton(
                                title = topList.name, onClick = {
                                    navController.navigate(
                                        PlaylistNav(
                                            playlistId = topList.id,
                                            limit = topList.trackCount
                                        )
                                    )
                                }, modifier = Modifier
                                    .padding(6.dp)
                                    .width(180.dp)
                            )
                        }
                    }
                }
            }

            item {
                newAlbumState?.weekData?.let {
                    NavigationTitle(
                        title = stringResource(R.string.newest_album_week),
                        modifier = Modifier.animateItem(),
                    )
                    LazyRow {
                        items(it) { album ->
                            with(sharedTransitionScope) {
                                NewestAlbumItem(
                                    album = album, modifier = Modifier
                                        .clip(
                                            MaterialTheme.shapes.small
                                        )
                                        .clickable {
                                            navController.navigate(AlbumNav(albumId = album.id))
                                        }, thumbnailContent = {
                                        AsyncImage(
                                            model = album.picUrl,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .sharedElement(
                                                    sharedTransitionScope.rememberSharedContentState(
                                                        key = album.id
                                                    ),
                                                    animatedVisibilityScope = animatedContentScope
                                                )
                                                .clip(
                                                    RoundedCornerShape(
                                                        ThumbnailCornerRadius
                                                    )
                                                )
                                        )
                                    })
                            }
                        }
                    }
                }
            }
            item {
                newAlbumState?.monthData?.let {
                    NavigationTitle(
                        title = stringResource(R.string.newest_album_month),
                        modifier = Modifier.animateItem(),
                    )
                    LazyRow {
                        items(it) { album ->
                            with(sharedTransitionScope) {
                                NewestAlbumItem(
                                    album = album,
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
                                        .clickable {
                                            navController.navigate(AlbumNav(albumId = album.id))
                                        },
                                    thumbnailContent = {
                                        AsyncImage(
                                            model = album.picUrl,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .sharedElement(
                                                    sharedTransitionScope.rememberSharedContentState(
                                                        key = album.id
                                                    ),
                                                    animatedVisibilityScope = animatedContentScope
                                                )
                                                .clip(
                                                    RoundedCornerShape(
                                                        ThumbnailCornerRadius
                                                    )
                                                )
                                        )
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}