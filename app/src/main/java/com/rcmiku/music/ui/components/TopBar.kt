package com.rcmiku.music.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.rcmiku.music.R
import com.rcmiku.music.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, @StringRes titleRes: Int) = TopAppBar(
    title = { Text(stringResource(titleRes)) },
    actions = {
        IconButton(onClick = {
            navController.navigate(Screen.Search.route)
        }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(
                    R.string.search
                )
            )
        }
        IconButton(onClick = {
            navController.navigate(Screen.Settings.route)
        }) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = stringResource(
                    R.string.settings
                )
            )
        }
    })