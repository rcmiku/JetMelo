package com.rcmiku.music.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.media3.common.MediaMetadata
import androidx.navigation.NavHostController
import com.rcmiku.music.constants.DURATION
import com.rcmiku.music.constants.DURATION_ENTER
import com.rcmiku.music.constants.DURATION_EXIT
import com.rcmiku.music.constants.DURATION_EXIT_SHORT
import com.rcmiku.music.constants.EmphasizedAccelerateEasing
import com.rcmiku.music.constants.EmphasizedDecelerateEasing
import com.rcmiku.music.constants.EmphasizedEasing
import com.rcmiku.music.ui.components.Lyric
import com.rcmiku.music.ui.components.MiniPlayer
import com.rcmiku.music.ui.components.Player
import com.rcmiku.music.ui.components.PlayerQueue

// copy from https://gist.github.com/JunkFood02/caf2af3cee41f847c0ad0bcf4d0cf9d8
@OptIn(ExperimentalSharedTransitionApi::class)
val AlbumArtBoundsTransform = BoundsTransform { _, _ ->
    tween(easing = EmphasizedEasing, durationMillis = DURATION)

}

fun <T> tweenEnter(
    delayMillis: Int = DURATION_EXIT,
    durationMillis: Int = DURATION_ENTER
) = tween<T>(
    delayMillis = delayMillis,
    durationMillis = durationMillis,
    easing = EmphasizedDecelerateEasing
)

fun <T> tweenExit(
    durationMillis: Int = DURATION_EXIT_SHORT,
) = tween<T>(
    durationMillis = durationMillis,
    easing = EmphasizedAccelerateEasing
)

const val FULL_PLAYER = 0
const val PLAY_QUEUE = 1
const val MINI_PLAYER = 2
const val LYRIC_VIEW = 3

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PlayerTransform(
    onClick: () -> Unit = {},
    onBackPressed: () -> Unit = {},
    mediaMetadata: MediaMetadata,
    position: Long,
    duration: Long,
    onPositionUpdate: (Long) -> Unit,
    navController: NavHostController,
) {

    var show by remember {
        mutableIntStateOf(MINI_PLAYER)
    }

    SharedTransitionLayout(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        AnimatedContent(targetState = show, transitionSpec = {
            fadeIn(
                tweenEnter(delayMillis = DURATION_EXIT_SHORT)
            ) togetherWith fadeOut(
                tweenExit(durationMillis = DURATION_EXIT_SHORT)
            )
        }) {
            when (it) {
                FULL_PLAYER -> {
                    Player(
                        navController = navController,
                        mediaMetadata = mediaMetadata,
                        duration = duration,
                        position = position,
                        imageModifier = Modifier.sharedElement(
                            state = rememberSharedContentState(
                                key = mediaMetadata.artist.toString()
                            ),
                            animatedVisibilityScope = this,
                            placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize,
                            boundsTransform = AlbumArtBoundsTransform,
                        ),
                        modifier = Modifier.sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = "container"
                            ),
                            animatedVisibilityScope = this,
                            placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize,
                            boundsTransform = AlbumArtBoundsTransform,
                            enter = fadeIn(
                                tweenEnter(delayMillis = DURATION_EXIT_SHORT)
                            ),
                            exit = fadeOut(
                                tweenExit(durationMillis = DURATION_EXIT_SHORT)
                            )
                        ),
                        onBackPressed = {
                            show = MINI_PLAYER
                            onBackPressed()
                        }, onClick = {
                            show = LYRIC_VIEW
                        },
                        onContainerClick = {
                            show = PLAY_QUEUE
                        },
                        onPositionUpdate = { position ->
                            onPositionUpdate(position)
                        }
                    )
                }

                PLAY_QUEUE -> {
                    PlayerQueue(
                        mediaMetadata = mediaMetadata,
                        imageModifier = Modifier.sharedElement(
                            state = rememberSharedContentState(
                                key = mediaMetadata.artist.toString()
                            ),
                            animatedVisibilityScope = this,
                            placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize,
                            boundsTransform = AlbumArtBoundsTransform,
                        ),
                        modifier = Modifier.sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = "container"
                            ),
                            animatedVisibilityScope = this,
                            placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize,
                            boundsTransform = AlbumArtBoundsTransform,
                            enter = fadeIn(
                                tweenEnter(delayMillis = DURATION_EXIT_SHORT)
                            ),
                            exit = fadeOut(
                                tweenExit(durationMillis = DURATION_EXIT_SHORT)
                            )
                        ),
                        onBackPressed = { show = FULL_PLAYER },
                    )
                }

                LYRIC_VIEW -> {
                    Lyric(
                        position = position,
                        mediaMetadata = mediaMetadata,
                        imageModifier = Modifier.sharedElement(
                            state = rememberSharedContentState(
                                key = mediaMetadata.artist.toString()
                            ),
                            animatedVisibilityScope = this,
                            placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize,
                            boundsTransform = AlbumArtBoundsTransform,
                        ),
                        modifier = Modifier.sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = "container"
                            ),
                            animatedVisibilityScope = this,
                            placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize,
                            boundsTransform = AlbumArtBoundsTransform,
                            enter = fadeIn(
                                tweenEnter(delayMillis = DURATION_EXIT_SHORT)
                            ),
                            exit = fadeOut(
                                tweenExit(durationMillis = DURATION_EXIT_SHORT)
                            )
                        ),
                        onBackPressed = { show = FULL_PLAYER },
                    )
                }

                else -> {
                    MiniPlayer(
                        mediaMetadata = mediaMetadata,
                        duration = duration,
                        position = position,
                        onClick = {
                            show = FULL_PLAYER
                            onClick()
                        },
                        imageModifier = Modifier.sharedElement(
                            state = rememberSharedContentState(
                                key = mediaMetadata.artist.toString()
                            ),
                            animatedVisibilityScope = this,
                            placeHolderSize = { contentSize: IntSize, animatedSize: IntSize ->
                                IntSize(contentSize.width, animatedSize.height)
                            },
                            boundsTransform = AlbumArtBoundsTransform,
                        ),
                        modifier = Modifier.sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = "container"
                            ),
                            animatedVisibilityScope = this,
                            placeHolderSize = SharedTransitionScope.PlaceHolderSize.contentSize,
                            boundsTransform = AlbumArtBoundsTransform,
                            enter = fadeIn(
                                tweenEnter(delayMillis = DURATION_EXIT)
                            ),
                            exit = fadeOut(
                                tweenExit(durationMillis = DURATION_EXIT_SHORT)
                            )
                        ),
                    )
                }
            }
        }
    }
}