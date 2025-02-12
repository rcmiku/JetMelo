package com.rcmiku.music.ui.screen

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.rcmiku.music.R
import com.rcmiku.music.ui.components.AlbumListItem
import com.rcmiku.music.ui.components.ListThumbnailImage
import com.rcmiku.music.ui.navigation.AlbumNav
import com.rcmiku.music.viewModel.AlbumSublistScreenViewmodel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun AlbumSublistScreen(
    navController: NavHostController,
    albumSublistScreenViewmodel: AlbumSublistScreenViewmodel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val albumSublist = albumSublistScreenViewmodel.albumSublist.collectAsLazyPagingItems()

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.my_album),
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
        LazyColumn(
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(albumSublist.itemCount) { index ->
                albumSublist[index]?.let {
                    with(sharedTransitionScope) {
                        AlbumListItem(
                            album = it, thumbnailContent = {
                                ListThumbnailImage(
                                    url = it.picUrl,
                                    modifier = Modifier.sharedElement(
                                        sharedTransitionScope.rememberSharedContentState(
                                            key = it.id
                                        ),
                                        animatedVisibilityScope = animatedContentScope
                                    )
                                )
                            }, modifier = Modifier
                                .clickable {
                                    navController.navigate(AlbumNav(albumId = it.id))
                                })
                    }

                }
            }
            item {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }
}