package com.rcmiku.music.ui.screen

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.rcmiku.music.LocalPlayerController
import com.rcmiku.music.LocalPlayerState
import com.rcmiku.music.R
import com.rcmiku.music.extensions.playMediaAt
import com.rcmiku.music.extensions.playMediaAtId
import com.rcmiku.music.extensions.setRadioPlaylist
import com.rcmiku.music.ui.components.RadioListItem
import com.rcmiku.music.ui.components.RadioThumbnailImage
import com.rcmiku.music.ui.icons.PlayArrow
import com.rcmiku.music.utils.formatPlayCount
import com.rcmiku.music.viewModel.ProgramRadioScreenViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ProgramRadioScreen(
    navController: NavController,
    programRadioScreenViewModel: ProgramRadioScreenViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {

    val radioInfo by programRadioScreenViewModel.radioInfo.collectAsState()
    val radioList = programRadioScreenViewModel.radioList.collectAsLazyPagingItems()
    val mediaController = LocalPlayerController.current.controller
    val playerState = LocalPlayerState.current
    val isPlaying = playerState?.isPlaying == true
    val currentMediaId = playerState?.currentMediaItem?.mediaId?.toLongOrNull()
    val listState = rememberLazyListState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.voice_list),
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
        radioInfo?.let {
            LazyColumn(
                contentPadding = padding, state = listState,
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            RadioThumbnailImage(
                                url = it.data.picUrl,
                            )
                            FilledIconButton(
                                modifier = Modifier.size(48.dp),
                                onClick = {
                                    radioList.itemSnapshotList.items.let {
                                        mediaController?.setRadioPlaylist(it)
                                        mediaController?.playMediaAt()
                                    }
                                },
                            ) {
                                Icon(
                                    PlayArrow,
                                    contentDescription = null,
                                )
                            }
                        }

                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = it.data.name,
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
                                formatPlayCount(it.data.playCount)
                            ),
                            style = MaterialTheme.typography.labelMedium,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(Modifier.height(6.dp))
                        it.data.desc?.let { description ->
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
                    }

                }

                items(radioList.itemCount) { index ->
                    radioList[index]?.let { item ->
                        RadioListItem(
                            radio = item,
                            isPlaying = isPlaying,
                            isActive = currentMediaId == item.mainSong.id,
                            modifier = Modifier.clickable {
                                radioList.itemSnapshotList.items.let {
                                    mediaController?.setRadioPlaylist(it)
                                    mediaController?.playMediaAtId(item.mainSong.id)
                                }
                            })
                    }
                }
            }
        }
    }
}