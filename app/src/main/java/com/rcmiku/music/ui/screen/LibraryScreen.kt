package com.rcmiku.music.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.rcmiku.music.R
import com.rcmiku.music.constants.ncmCookieKey
import com.rcmiku.music.ui.components.Dialog
import com.rcmiku.music.ui.components.LibraryListButton
import com.rcmiku.music.ui.components.NavigationTitle
import com.rcmiku.music.ui.components.TopBar
import com.rcmiku.music.ui.icons.Album
import com.rcmiku.music.ui.icons.Cloud
import com.rcmiku.music.ui.icons.Favorite
import com.rcmiku.music.ui.icons.Leaderboard
import com.rcmiku.music.ui.icons.Logout
import com.rcmiku.music.ui.icons.Star
import com.rcmiku.music.ui.icons.VipFill
import com.rcmiku.music.ui.navigation.CloudSongNav
import com.rcmiku.music.ui.navigation.PlaylistNav
import com.rcmiku.music.ui.navigation.RecordNav
import com.rcmiku.music.ui.navigation.Screen
import com.rcmiku.music.ui.navigation.UserPlayListNav
import com.rcmiku.music.utils.rememberPreference
import com.rcmiku.music.viewModel.LibraryScreenViewModel
import com.rcmiku.ncmapi.api.account.UserPlaylistType

@Composable
fun LibraryScreen(
    navController: NavHostController,
    libraryScreenViewModel: LibraryScreenViewModel = hiltViewModel()
) {

    var ncmCookie by rememberPreference(ncmCookieKey, "")
    val userInfoBatchState by libraryScreenViewModel.userInfo.collectAsState()
    val favoriteSongState by libraryScreenViewModel.favoriteSong.collectAsState()
    var logout by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(ncmCookie) {
        if (ncmCookie.isNotEmpty()) {
            libraryScreenViewModel.fetchUserInfo()
        }
    }

    Scaffold(topBar = {
        TopBar(navController = navController, titleRes = R.string.library)
    }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            userInfoBatchState?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    colors = CardDefaults.cardColors()
                        .copy(containerColor = MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .height(64.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(64.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            AsyncImage(
                                model = it.account.profile.avatarUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                            )
                            if (it.account.profile.vipType != 0)
                                Icon(
                                    imageVector = VipFill,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(24.dp)
                                )
                        }
                        Spacer(modifier = Modifier.width(12.dp))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = it.account.profile.nickname,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Badge(
                                content = {
                                    Text(
                                        text = stringResource(
                                            R.string.level,
                                            it.level.data.level
                                        ),
                                        fontSize = 10.sp,
                                        fontStyle = FontStyle.Italic
                                    )
                                }
                            )
                        }

                        IconButton(onClick = {
                            logout = true
                        }) {
                            Icon(
                                imageVector = Logout,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }
            }


            NavigationTitle(
                title = stringResource(R.string.mine),
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    LibraryListButton(
                        title = stringResource(R.string.favorite_music),
                        onClick = {
                            favoriteSongState?.data?.id?.let {
                                navController.navigate(PlaylistNav(playlistId = it, noCache = true))
                            }
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Favorite,
                                contentDescription = null,
                                Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        })
                }
                item {
                    LibraryListButton(
                        title = stringResource(R.string.favorite_album),
                        onClick = {
                            favoriteSongState?.data?.id?.let {
                                navController.navigate(Screen.AlbumSublist.route)
                            }
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Album,
                                contentDescription = null,
                                Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        })
                }
                item {
                    LibraryListButton(
                        title = stringResource(R.string.create_playlist),
                        onClick = {
                            userInfoBatchState?.account?.profile?.userId?.let {
                                navController.navigate(
                                    UserPlayListNav(
                                        userId = it,
                                        type = UserPlaylistType.CREATE.type
                                    )
                                )
                            }
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.List,
                                contentDescription = null,
                                Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        })
                }
                item {
                    LibraryListButton(
                        title = stringResource(R.string.collect_playlist),
                        onClick = {
                            userInfoBatchState?.account?.profile?.userId?.let {
                                navController.navigate(
                                    UserPlayListNav(
                                        userId = it,
                                        type = UserPlaylistType.COLLECT.type
                                    )
                                )
                            }
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Star,
                                contentDescription = null,
                                Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        })
                }
                item {
                    LibraryListButton(
                        title = stringResource(R.string.record),
                        onClick = {
                            userInfoBatchState?.account?.profile?.userId?.let {
                                navController.navigate(RecordNav(uid = it))
                            }
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Leaderboard,
                                contentDescription = null,
                                Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        })
                }
                item {
                    LibraryListButton(
                        title = stringResource(R.string.cloud_music),
                        onClick = {
                            userInfoBatchState?.account?.profile?.userId?.let {
                                navController.navigate(CloudSongNav(uid = it))
                            }
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Cloud,
                                contentDescription = null,
                                Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        })
                }
            }
        }
    }
    if (logout) {
        Dialog(
            onConfirmation = {
                ncmCookie = ""
                logout = false
            },
            onDismissRequest = {
                logout = false
            },
            dialogTitle = stringResource(R.string.logout),
        )
    }
}

