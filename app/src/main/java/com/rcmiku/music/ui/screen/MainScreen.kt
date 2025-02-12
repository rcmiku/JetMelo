package com.rcmiku.music.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.media3.common.Player.STATE_READY
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rcmiku.music.LocalPlayerState
import com.rcmiku.music.constants.MiniPlayerHeight
import com.rcmiku.music.constants.currentPlayMediaIdKey
import com.rcmiku.music.constants.ncmCookieKey
import com.rcmiku.music.constants.userIdKye
import com.rcmiku.music.ui.components.tabs
import com.rcmiku.music.ui.navigation.NavGraph
import com.rcmiku.music.utils.rememberPreference
import com.rcmiku.ncmapi.api.account.AccountApi
import com.rcmiku.ncmapi.utils.CookieProvider
import com.rcmiku.ncmapi.utils.json
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showNavigationBar =
        currentDestination?.hierarchy?.any { tabs.any { tab -> it.route == tab.route } } == true
    val ncmCookie by rememberPreference(ncmCookieKey, "")
    var userId by rememberPreference(userIdKye, 0)
    val playerState = LocalPlayerState.current
    var position by rememberSaveable(playerState) {
        mutableLongStateOf(playerState?.player?.currentPosition ?: 0)
    }
    var duration by rememberSaveable(playerState) {
        mutableLongStateOf(playerState?.player?.duration ?: 0)
    }
    val playbackState = playerState?.playbackState
    val showMiniPlayer =
        (playerState?.player?.mediaItemCount ?: 0) != 0
    val currentMediaId = playerState?.currentMediaItem?.mediaId
    var currentPlayMediaId by rememberPreference(currentPlayMediaIdKey, 0)
    LaunchedEffect(playbackState) {
        if (playbackState == STATE_READY) {
            while (isActive) {
                delay(1000)
                position = playerState.player.currentPosition
                duration = playerState.player.duration
            }
        }
    }

    LaunchedEffect(currentMediaId) {
        position = 0L
        currentMediaId?.toLongOrNull()?.let {
            currentPlayMediaId = it
        }
    }

    var showPlayer by remember { mutableStateOf(false) }

    LaunchedEffect(ncmCookie) {
        if (ncmCookie.isNotEmpty()) {
            CookieProvider.init(json.decodeFromString(ncmCookie))
            AccountApi.account().getOrNull()?.profile?.userId?.let {
                userId = it
            }
        }
    }

    Scaffold(
        bottomBar = {
            Column {
                AnimatedVisibility(
                    showNavigationBar, enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    NavigationBar {
                        tabs.forEach { item ->
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = stringResource(id = item.titleRes)
                                    )
                                },
                                label = { Text(stringResource(id = item.titleRes)) },
                                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                })
                        }
                    }
                }
            }

        },
        content = { padding ->

            var bottomPadding = if (!showPlayer) {
                padding.calculateBottomPadding()
            } else {
                0.dp
            }

            if (!showPlayer && !showNavigationBar && showMiniPlayer) {
                bottomPadding =
                    WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .zIndex(1f)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .defaultMinSize(minHeight = MiniPlayerHeight)
                        .windowInsetsPadding(WindowInsets(bottom = bottomPadding)),
                ) {
                    playerState?.mediaMetadata?.let {
                        PlayerTransform(
                            mediaMetadata = it, position = position, duration = duration,
                            onBackPressed = { showPlayer = false },
                            onClick = { showPlayer = true },
                            onPositionUpdate = { updatePosition ->
                                position = updatePosition
                            }, navController = navController
                        )

                    }
                }
            }

            NavGraph(
                navController = navController,
                bottomPadding = bottomPadding,
                showMiniPlayer = showMiniPlayer
            )
        }
    )
}