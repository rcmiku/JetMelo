package com.rcmiku.music.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rcmiku.music.LocalPlayerController
import com.rcmiku.music.LocalPlayerState
import com.rcmiku.music.R
import com.rcmiku.music.extensions.playMediaAtId
import com.rcmiku.music.extensions.setPlaylist
import com.rcmiku.music.ui.components.SongListItem
import com.rcmiku.music.viewModel.RecordScreenViewModel
import com.rcmiku.ncmapi.api.account.SongRecordType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    navController: NavHostController,
    recordScreenViewModel: RecordScreenViewModel = hiltViewModel()
) {

    var state by remember { mutableIntStateOf(0) }
    val titles = listOf(stringResource(R.string.week_record), stringResource(R.string.all_record))
    val songRecord by recordScreenViewModel.songRecord.collectAsState()
    val mediaController = LocalPlayerController.current.controller
    val playerState = LocalPlayerState.current
    val isPlaying = playerState?.isPlaying == true
    val currentMediaId = playerState?.currentMediaItem?.mediaId?.toLongOrNull()

    LaunchedEffect(state) {
        recordScreenViewModel.updateSongRecordType(if (state == 0) SongRecordType.WEEK else SongRecordType.ALL)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.record))
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
        },
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                Column {
                    SecondaryTabRow(
                        selectedTabIndex = state,
                        indicator = { FancyAnimatedIndicatorWithModifier(state) }
                    ) {
                        titles.forEachIndexed { index, title ->
                            Tab(
                                selected = state == index,
                                onClick = { state = index },
                                text = { Text(title) })
                        }
                    }
                }
            }
            songRecord?.weekData?.let { data ->
                itemsIndexed(data) { index, item ->
                    SongListItem(
                        song = item.song,
                        isPlaying = isPlaying,
                        isActive = currentMediaId == item.song.id,
                        songIndex = index + 1,
                        modifier = Modifier.clickable {
                            mediaController?.setPlaylist(data.map { it.song })
                            mediaController?.playMediaAtId(item.song.id)
                        },
                        trailingContent = {
                            Text(
                                text = stringResource(R.string.play_count, item.playCount),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FancyIndicator(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier
            .padding(5.dp)
            .fillMaxSize()
            .border(BorderStroke(2.dp, color), RoundedCornerShape(5.dp))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabIndicatorScope.FancyAnimatedIndicatorWithModifier(index: Int) {
    val colors =
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.tertiary,
        )
    var startAnimatable by remember { mutableStateOf<Animatable<Dp, AnimationVector1D>?>(null) }
    var endAnimatable by remember { mutableStateOf<Animatable<Dp, AnimationVector1D>?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val indicatorColor: Color by animateColorAsState(colors[index % colors.size], label = "")

    Box(
        Modifier
            .tabIndicatorLayout { measurable: Measurable,
                                  constraints: Constraints,
                                  tabPositions: List<TabPosition> ->
                val newStart = tabPositions[index].left
                val newEnd = tabPositions[index].right
                val startAnim =
                    startAnimatable
                        ?: Animatable(newStart, Dp.VectorConverter).also { startAnimatable = it }

                val endAnim =
                    endAnimatable
                        ?: Animatable(newEnd, Dp.VectorConverter).also { endAnimatable = it }

                if (endAnim.targetValue != newEnd) {
                    coroutineScope.launch {
                        endAnim.animateTo(
                            newEnd,
                            animationSpec =
                                if (endAnim.targetValue < newEnd) {
                                    spring(dampingRatio = 1f, stiffness = 1000f)
                                } else {
                                    spring(dampingRatio = 1f, stiffness = 50f)
                                }
                        )
                    }
                }

                if (startAnim.targetValue != newStart) {
                    coroutineScope.launch {
                        startAnim.animateTo(
                            newStart,
                            animationSpec =
                                if (startAnim.targetValue < newStart) {
                                    spring(dampingRatio = 1f, stiffness = 50f)
                                } else {
                                    spring(dampingRatio = 1f, stiffness = 1000f)
                                }
                        )
                    }
                }

                val indicatorEnd = endAnim.value.roundToPx()
                val indicatorStart = startAnim.value.roundToPx()

                val placeable =
                    measurable.measure(
                        constraints.copy(
                            maxWidth = indicatorEnd - indicatorStart,
                            minWidth = indicatorEnd - indicatorStart,
                        )
                    )
                layout(constraints.maxWidth, constraints.maxHeight) {
                    placeable.place(indicatorStart, 0)
                }
            }
            .padding(5.dp)
            .fillMaxSize()
            .drawWithContent {
                drawRoundRect(
                    color = indicatorColor,
                    cornerRadius = CornerRadius(5.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
    )
}
