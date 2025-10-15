package com.example.tiktokandroid.core.presentation.view

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val defaultIcon: ImageVector,
    val route: String
)