package com.rcmiku.music.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rcmiku.music.R
import com.rcmiku.music.ui.icons.Remove
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onTimeSet: (Long) -> Unit
) {
    val maxMinutes = 12.hours
    val minMinutes = 5.minutes
    val setTimes = remember { mutableStateOf(5.minutes) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.sleep_timer)) },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    InteractiveButton(onClick = {
                        if (setTimes.value - 5.minutes >= minMinutes) {
                            setTimes.value -= 5.minutes
                        }
                    }, onLongPress = {
                        if (setTimes.value - 5.minutes >= minMinutes) {
                            setTimes.value -= 5.minutes
                        }
                    }, icon = Remove)

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(16.dp)
                            .width(100.dp)
                    ) {
                        Text(
                            text = setTimes.value.toComponents { hours, minutes, _, _ ->
                                "%02dh:%02dm".format(hours, minutes)
                            },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }


                    InteractiveButton(onClick = {
                        if (setTimes.value + 5.minutes <= maxMinutes) {
                            setTimes.value += 5.minutes
                        }
                    }, onLongPress = {
                        if (setTimes.value + 5.minutes <= maxMinutes) {
                            setTimes.value += 5.minutes
                        }
                    }, icon = Icons.Default.Add)

                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onTimeSet(setTimes.value.inWholeSeconds)
            }) {
                Text(text = stringResource(R.string.settings))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}


@Composable
fun InteractiveButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    longPressDelay: Duration = 500.milliseconds,
    repeatInterval: Duration = 100.milliseconds,
    onLongPress: () -> Unit = {},
    size: Dp = 56.dp,
    padding: Dp = 12.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val scope = rememberCoroutineScope()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val buttonInteracted = isPressed || isHovered

    val animateColor by animateColorAsState(
        targetValue = if (buttonInteracted) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
        animationSpec = tween(300),
    )

    val animateIconColor by animateColorAsState(
        targetValue = if (buttonInteracted) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onPrimary,
        animationSpec = tween(300),
    )

    val animateIconSize by animateFloatAsState(
        targetValue = if (buttonInteracted) 1.4f else 1f,
        animationSpec = tween(300)
    )

    Surface(
        shape = CircleShape,
        color = animateColor,
        modifier = modifier
            .size(size)
            .scale(animateIconSize)
            .padding(padding)
            .clip(CircleShape)
            .pointerInput(Unit) {
                awaitEachGesture {
                    while (true) {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        val pressJob = scope.launch {
                            delay(longPressDelay)
                            while (down.pressed) {
                                onLongPress()
                                delay(repeatInterval)
                            }
                        }
                        waitForUpOrCancellation()
                        pressJob.cancel()
                    }
                }
            }
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = onClick
            )
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = animateIconColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
