package com.rcmiku.music.ui.screen

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.rcmiku.music.R
import com.rcmiku.music.constants.GridThumbnailHeight
import com.rcmiku.music.constants.ThumbnailCornerRadius
import com.rcmiku.music.ui.components.PlaylistGridItem
import com.rcmiku.music.ui.navigation.PlaylistNav
import com.rcmiku.music.viewModel.ExploreScreenViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ListScreen(
    navController: NavHostController,
    exploreScreenViewModel: ExploreScreenViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val topListState by exploreScreenViewModel.topList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.top_list),
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
        LazyVerticalGrid(
            contentPadding = padding,
            columns = GridCells.Adaptive(GridThumbnailHeight),
        ) {
            topListState?.onSuccess { topList ->
                items(topList.list) {
                    with(sharedTransitionScope) {
                        PlaylistGridItem(
                            playlist = it, maxLine = 2, modifier = Modifier
                                .fillMaxSize()
                                .clip(MaterialTheme.shapes.small)
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
                                AsyncImage(
                                    model = it.picUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .sharedElement(
                                            sharedTransitionScope.rememberSharedContentState(
                                                key = it.id
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