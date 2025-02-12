package com.rcmiku.music.ui.screen

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rcmiku.music.R
import com.rcmiku.music.constants.AlbumThumbnailSize
import com.rcmiku.music.ui.components.GridThumbnailImage
import com.rcmiku.music.ui.components.PlaylistGridItem
import com.rcmiku.music.ui.navigation.PlaylistNav
import com.rcmiku.music.viewModel.UserPlaylistScreenViewModel
import com.rcmiku.ncmapi.api.account.UserPlaylistType

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun UserPlaylistScreen(
    navController: NavHostController,
    userPlaylistScreenViewModel: UserPlaylistScreenViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val type = userPlaylistScreenViewModel.userPlaylistType
    val playlistState by userPlaylistScreenViewModel.playlist.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = when (type) {
                        UserPlaylistType.CREATE -> stringResource(R.string.create_playlist)
                        UserPlaylistType.COLLECT -> stringResource(R.string.collect_playlist)
                        null -> {
                            ""
                        }
                    },
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
    }) { padding ->
        LazyVerticalGrid(
            contentPadding = padding,
            columns = GridCells.Adaptive(AlbumThumbnailSize),
        ) {
            with(sharedTransitionScope) {
                playlistState?.let { playlist ->
                    items(playlist.data.playlist) {
                        PlaylistGridItem(
                            playlist = it, maxLine = 2, modifier = Modifier
                                .fillMaxSize()
                                .clickable(
                                    onClick = {
                                        navController.navigate(
                                            PlaylistNav(
                                                playlistId = it.id,
                                                limit = it.trackCount
                                            )
                                        )
                                    },
                                ), thumbnailContent = {
                                GridThumbnailImage(
                                    url = it.picUrl,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .sharedElement(
                                            sharedTransitionScope.rememberSharedContentState(
                                                key = it.id
                                            ),
                                            animatedVisibilityScope = animatedContentScope
                                        )
                                )
                            })
                    }
                }
            }
        }
    }
}