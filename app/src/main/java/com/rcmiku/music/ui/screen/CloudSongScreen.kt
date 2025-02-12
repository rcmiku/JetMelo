package com.rcmiku.music.ui.screen

import androidx.compose.foundation.clickable
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.rcmiku.music.LocalPlayerController
import com.rcmiku.music.LocalPlayerState
import com.rcmiku.music.R
import com.rcmiku.music.extensions.playMediaAtId
import com.rcmiku.music.extensions.setCloudSongPlaylist
import com.rcmiku.music.ui.components.CloudSongListItem
import com.rcmiku.music.viewModel.CloudSongScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CloudSongScreen(
    navController: NavHostController,
    cloudSongScreenViewModel: CloudSongScreenViewModel = hiltViewModel()
) {

    val cloudSong = cloudSongScreenViewModel.cloudSong.collectAsLazyPagingItems()
    val mediaController = LocalPlayerController.current.controller
    val playerState = LocalPlayerState.current
    val isPlaying = playerState?.isPlaying == true
    val currentMediaId = playerState?.currentMediaItem?.mediaId?.toLongOrNull()
    val uid = cloudSongScreenViewModel.uid

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.cloud_music),
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
        ) {
            items(cloudSong.itemCount) { index ->
                cloudSong[index]?.let { item ->
                    CloudSongListItem(
                        songIndex = index + 1,
                        cloudSong = item,
                        isPlaying = isPlaying,
                        isActive = currentMediaId == item.simpleSong.id,
                        modifier = Modifier.clickable {
                            cloudSong.itemSnapshotList.items.let {
                                if (uid != null)
                                    mediaController?.setCloudSongPlaylist(
                                        uid = uid,
                                        cloudSongs = it
                                    )
                                mediaController?.playMediaAtId(item.simpleSong.id)
                            }
                        })
                }
            }
        }
    }
}