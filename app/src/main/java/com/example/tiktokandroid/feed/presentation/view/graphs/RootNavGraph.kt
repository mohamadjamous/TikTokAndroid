package com.example.tiktokandroid.feed.presentation.view.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.example.tiktokandroid.core.presentation.view.Screen
import com.example.tiktokandroid.explore.presentation.view.screens.ExploreScreen
import com.example.tiktokandroid.feed.presentation.view.screens.FeedScreen
import com.example.tiktokandroid.notifications.presentation.view.screens.NotificationsScreen
import com.example.tiktokandroid.profile.presentation.view.screens.ProfileScreen
import com.example.tiktokandroid.uploadmedia.presentation.view.UploadScreen

@Composable
fun RootNavGraph(navController: NavHostController) {

    val graph =
        navController.createGraph(startDestination = Screen.Home.rout) {
            composable(route = Screen.Home.rout) {
                FeedScreen()
            }
            composable(route = Screen.Friends.rout) {
                ExploreScreen()
            }
            composable(route = Screen.Upload.rout) {
                UploadScreen()
            }
            composable(route = Screen.Notifications.rout) {
                NotificationsScreen()
            }
            composable(route = Screen.Profile.rout) {
                ProfileScreen()
            }
        }


    NavHost(
        navController = navController,
        graph = graph
    )
}