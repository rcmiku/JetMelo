package com.rcmiku.music.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rcmiku.music.R
import com.rcmiku.music.constants.SettingItemCorner
import com.rcmiku.music.constants.SettingItemHeight
import com.rcmiku.music.constants.SettingItemSubCorner
import com.rcmiku.music.constants.audioQualityKey
import com.rcmiku.music.constants.autoSkipNextOnErrorKey
import com.rcmiku.music.constants.ncmCookieKey
import com.rcmiku.music.constants.use40DpIconKey
import com.rcmiku.music.ui.components.Dialog
import com.rcmiku.music.ui.components.SongQualityDialog
import com.rcmiku.music.ui.icons.Github
import com.rcmiku.music.ui.icons.GraphicEq
import com.rcmiku.music.ui.icons.Login
import com.rcmiku.music.ui.icons.Logout
import com.rcmiku.music.ui.icons.PlayPause
import com.rcmiku.music.ui.icons.SkipNext
import com.rcmiku.music.ui.icons.UserRound
import com.rcmiku.music.ui.navigation.Screen
import com.rcmiku.music.utils.getItemShape
import com.rcmiku.music.utils.rememberEnumPreference
import com.rcmiku.music.utils.rememberPreference
import com.rcmiku.ncmapi.api.player.SongLevel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    val uriHandler = LocalUriHandler.current

    var use40DpIcon by rememberPreference(use40DpIconKey, false)
    var audioQuality by rememberEnumPreference(audioQualityKey, defaultValue = SongLevel.STANDARD)
    var showDialog by remember { mutableStateOf(false) }
    var autoSkipNextOnError by rememberPreference(autoSkipNextOnErrorKey, false)
    var logout by rememberSaveable { mutableStateOf(false) }
    var ncmCookie by rememberPreference(ncmCookieKey, "")

    val baseSettingItems = listOf(
        SettingItemData(
            title = stringResource(if (ncmCookie.isNotEmpty()) R.string.logout else R.string.login),
            imageVector = if (ncmCookie.isNotEmpty()) Logout else Login,
            onClick = {
                if (ncmCookie.isNotEmpty())
                    logout = true
                else
                    navController.navigate(Screen.Login.route)
            }
        ),
        SettingItemData(
            title = stringResource(R.string.command_button),
            subtitle = stringResource(R.string.command_button_subtitle),
            imageVector = PlayPause,
            trailingContent = {
                Switch(
                    checked = use40DpIcon,
                    onCheckedChange = {
                        use40DpIcon = it
                    }
                )
                Spacer(Modifier.width(12.dp))
            },
            onClick = {
                use40DpIcon = !use40DpIcon
            }
        ), SettingItemData(
            title = stringResource(R.string.audio_quality),
            subtitle = when (audioQuality) {
                SongLevel.STANDARD -> stringResource(R.string.standard)
                SongLevel.HIGHER -> stringResource(R.string.higer)
                SongLevel.EXHIGH -> stringResource(R.string.exhigh)
                SongLevel.LOSSLESS -> stringResource(R.string.lossless)
                SongLevel.HIRES -> stringResource(R.string.hi_res)
//                SongLevel.JYEFFECT -> stringResource(R.string.jyeffect)
//                SongLevel.SKY -> stringResource(R.string.sky)
//                SongLevel.JYMASTER -> stringResource(R.string.jymaster)
            },
            imageVector = GraphicEq,
            trailingContent = {

            },
            onClick = {
                showDialog = true
            }
        ),
        SettingItemData(
            title = stringResource(R.string.auto_skip),
            imageVector = SkipNext,
            trailingContent = {
                Switch(
                    checked = autoSkipNextOnError,
                    onCheckedChange = {
                        autoSkipNextOnError = it
                    }
                )
                Spacer(Modifier.width(12.dp))
            },
            onClick = {
                autoSkipNextOnError = !autoSkipNextOnError
            }
        )
    )


    val settingsItems = listOf(
        SettingItemData(
            title = stringResource(R.string.dev),
            subtitle = "rcmiku",
            imageVector = UserRound,
            onClick = { uriHandler.openUri("https://github.com/rcmiku") }
        ),
        SettingItemData(
            title = stringResource(R.string.source_code),
            imageVector = Github,
            onClick = { uriHandler.openUri("https://github.com/rcmiku/JetMelo") }
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
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
        LazyColumn(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            contentPadding = padding
        ) {
            item {
                Text(
                    stringResource(R.string.basic_settings),
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }

            itemsIndexed(baseSettingItems) { index, item ->
                val shape = getItemShape(
                    prevItem = baseSettingItems.getOrNull(index - 1),
                    nextItem = baseSettingItems.getOrNull(index + 1),
                    corner = SettingItemCorner,
                    subCorner = SettingItemSubCorner,
                )

                SettingCard(
                    title = item.title,
                    description = item.subtitle,
                    shape = shape,
                    imageVector = item.imageVector,
                    onClick = item.onClick,
                    trailingContent = item.trailingContent
                )
            }

            item {
                Text(
                    stringResource(R.string.about),
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }

            itemsIndexed(settingsItems) { index, item ->
                val shape = getItemShape(
                    prevItem = settingsItems.getOrNull(index - 1),
                    nextItem = settingsItems.getOrNull(index + 1),
                    corner = SettingItemCorner,
                    subCorner = SettingItemSubCorner,
                )
                SettingCard(
                    title = item.title,
                    description = item.subtitle,
                    imageVector = item.imageVector,
                    shape = shape,
                    onClick = item.onClick,
                    trailingContent = item.trailingContent,
                )
            }
        }
    }

    if (showDialog) {
        SongQualityDialog(
            currentLevel = audioQuality,
            onDismiss = { showDialog = false },
            onQualitySelected = { audioQuality = it }
        )
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

@Composable
fun SettingCard(
    title: String,
    description: String? = null,
    imageVector: ImageVector,
    shape: Shape,
    onClick: (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        SettingItem(
            imageVector = imageVector,
            title = title,
            description = description,
            onClick = onClick,
            trailingContent = trailingContent
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingItem(
    imageVector: ImageVector,
    title: String,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = SettingItemHeight)
            .combinedClickable(
                onClick = {
                    onClick?.invoke()
                },
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceTint),
            modifier = Modifier.padding(start = 12.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        trailingContent?.invoke()
    }
}

data class SettingItemData(
    val title: String,
    val subtitle: String? = null,
    val imageVector: ImageVector,
    val onClick: (() -> Unit)? = null,
    val trailingContent: @Composable (() -> Unit)? = null
)
