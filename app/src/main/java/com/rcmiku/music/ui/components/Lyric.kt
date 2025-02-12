package com.rcmiku.music.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaMetadata
import coil3.compose.AsyncImage
import com.rcmiku.music.LocalPlayerController
import com.rcmiku.music.LocalPlayerState
import com.rcmiku.music.constants.MediaItemHeight
import com.rcmiku.music.utils.parseLrc
import com.rcmiku.music.viewModel.LyricViewModel
import kotlinx.coroutines.launch

@Composable
fun Lyric(
    position: Long,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    mediaMetadata: MediaMetadata,
    onBackPressed: () -> Unit = {},
    lyricViewModel: LyricViewModel = hiltViewModel()
) {
    val mediaController = LocalPlayerController.current.controller
    val playerState = LocalPlayerState.current
    val currentMediaId = playerState?.currentMediaItem?.mediaId
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val lyric by lyricViewModel.lyric.collectAsState()
    val lrcLine = lyric?.lrc?.lyric?.parseLrc()
    var currentIndex by remember { mutableIntStateOf(0) }
    var autoScrollEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(currentMediaId) {
        currentIndex = 0
        coroutineScope.launch {
            currentMediaId?.let {
                lyricViewModel.fetchLyric(it.toLong())
            }
        }
    }

    BackHandler {
        onBackPressed()
    }

    KeepScreenOn()

    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.fillMaxHeight(),
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(12.dp)
                    .clip(MaterialTheme.shapes.small)
                    .clickable { onBackPressed() }
            ) {
                AsyncImage(
                    model = mediaMetadata.artworkUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = imageModifier
                        .size(MediaItemHeight)
                        .clip(MaterialTheme.shapes.small)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .then(modifier)
                ) {
                    Text(
                        text = mediaMetadata.title.toString(),
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.basicMarquee()
                    )
                    Text(
                        text = mediaMetadata.artist.toString(),
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.basicMarquee()
                    )
                }
            }

            lrcLine?.let { lrcLines ->

                LaunchedEffect(listState.isScrollInProgress) {
                    autoScrollEnabled = !listState.isScrollInProgress
                }

                LaunchedEffect(position) {
                    val index = lrcLines.indexOfLast { it.time <= position }
                    if (index != currentIndex) {
                        currentIndex = index
                        if (autoScrollEnabled) {
                            coroutineScope.launch {
                                if (index > 0) {
                                    val targetIndex = maxOf(currentIndex - 2, 0)
                                    val visibleItem =
                                        listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == targetIndex }
                                    if (visibleItem == null) {
                                        listState.scrollToItem(targetIndex)
                                    } else {
                                        val itemOffset = visibleItem.offset
                                        listState.animateScrollBy(
                                            itemOffset.toFloat(),
                                            animationSpec = tween(
                                                durationMillis = 500,
                                                easing = EaseInOutCubic
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(
                        count = lrcLines.size,
                    ) { index ->
                        val isCurrent = index == currentIndex
                        val currentText = lrcLines[index].text.isNotEmpty()

                        if (currentText)
                            Text(
                                text = lrcLines[index].text,
                                color = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 32.sp,
                                lineHeight = 1.2.em,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(MaterialTheme.shapes.small)
                                    .clickable {
                                        lrcLines[index].time.let {
                                            mediaController?.seekTo(it)
                                            coroutineScope.launch {
                                                currentIndex = index
                                            }
                                        }
                                    }
                                    .padding(vertical = 8.dp, horizontal = 4.dp)
                                    .alpha(if (isCurrent) 1f else 0.5f)
                            )
                        else
                            Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                                if (isCurrent) {
                                    lrcLines.getOrNull(index + 1)?.time?.let { time ->
                                        ThreeDotsAnimation(
                                            times = lrcLines[index].time to time,
                                        )
                                    }
                                }
                            }
                    }
                    item {
                        Spacer(Modifier.padding(vertical = 24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ThreeDotsAnimation(
    modifier: Modifier = Modifier,
    dotColor: Color = MaterialTheme.colorScheme.primary,
    dotSize: Dp = 10.dp,
    times: Pair<Long, Long>,
) {
    if (times.let { it.second - it.first } < 6000L)
        return

    val duration = times.second - times.first
    val transition = rememberInfiniteTransition()
    val scale by
    transition.animateFloat(
        initialValue = 1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                1f at 0 using EaseInOutBack
                1.2f at 1500 using EaseInOutBack
                1f at 3000 using EaseInOutBack
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    val alphaAnimations = List(3) { index ->
        transition.animateFloat(
            initialValue = 0.4f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = duration.toInt()
                    val start = index * (duration.toInt() / 3)
                    0.5f at start
                    1.0f at start + duration.toInt() / 6
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Canvas(
        modifier = modifier
            .size(48.dp, 16.dp)
            .scale(scale)
    ) {
        val space = 16.dp.toPx()
        val centerY = size.height / 2
        val baseRadius = dotSize.toPx() / 2

        repeat(3) { i ->
            drawCircle(
                color = dotColor.copy(alpha = alphaAnimations[i].value),
                radius = baseRadius,
                center = Offset(
                    x = size.width / 2 - space + i * space,
                    y = centerY
                )
            )
        }
    }
}
