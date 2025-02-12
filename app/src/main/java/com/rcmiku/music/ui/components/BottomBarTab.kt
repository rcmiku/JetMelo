package com.rcmiku.music.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.rcmiku.music.R
import com.rcmiku.music.ui.icons.ExploreMusic
import com.rcmiku.music.ui.icons.LibraryMusic
import com.rcmiku.music.ui.navigation.Screen

sealed class BottomBarTab(@StringRes val titleRes: Int, val icon: ImageVector, val route: String) {
    data object Home : BottomBarTab(
        titleRes = R.string.home,
        icon = Icons.Outlined.Home,
        route = Screen.Home.route
    )

    data object Explore : BottomBarTab(
        titleRes = R.string.explore,
        icon = ExploreMusic,
        route = Screen.Explore.route
    )

    data object Library : BottomBarTab(
        titleRes = R.string.library,
        icon = LibraryMusic,
        route = Screen.Library.route
    )
}

val tabs = listOf(
    BottomBarTab.Home,
    BottomBarTab.Explore,
    BottomBarTab.Library,
)