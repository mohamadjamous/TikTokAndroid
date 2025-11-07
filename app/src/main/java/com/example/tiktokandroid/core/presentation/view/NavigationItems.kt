package com.example.tiktokandroid.core.presentation.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import com.example.tiktokandroid.core.presentation.model.Screen

val navigationItems = listOf(
    NavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        defaultIcon = Icons.Outlined.Home,
        route = Screen.Home.route
    ),

    NavigationItem(
        title = "Friends",
        selectedIcon = Icons.Filled.People,
        defaultIcon = Icons.Outlined.People,
        route = Screen.Friends.route
    ),

    NavigationItem(
        title = "",
        selectedIcon = Icons.Filled.Add,
        defaultIcon = Icons.Outlined.Add,
        route = Screen.Upload.route
    ),

    NavigationItem(
        title = "Inbox",
        selectedIcon = Icons.Filled.Favorite,
        defaultIcon = Icons.Outlined.FavoriteBorder,
        route = Screen.Notifications.route
    ),
    NavigationItem(
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        defaultIcon = Icons.Outlined.Person,
        route = Screen.Profile.route
    ),
)