package com.example.tiktokandroid.feed.presentation.view.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tiktokandroid.core.presentation.model.Screen
import com.example.tiktokandroid.feed.presentation.view.screens.FeedScreen

/**
 * Created by Puskal Khadka on 3/14/2023.
 */

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    composable(route = Screen.Home.route) {
//        FeedScreen()
    }
}