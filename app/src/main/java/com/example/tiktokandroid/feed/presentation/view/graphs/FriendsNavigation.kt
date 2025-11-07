package com.example.tiktokandroid.feed.presentation.view.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tiktokandroid.core.presentation.model.Screen
import com.example.tiktokandroid.explore.presentation.view.screens.ExploreScreen

/**
 * Created by Puskal Khadka on 3/14/2023.
 */

fun NavGraphBuilder.friendsNavGraph(navController: NavController) {
    composable(route = Screen.Home.route) {
        ExploreScreen()
    }
}