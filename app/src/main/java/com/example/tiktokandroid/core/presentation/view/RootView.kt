package com.example.tiktokandroid.core.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tiktokandroid.core.presentation.components.BottomNavigationBar
import com.example.tiktokandroid.core.presentation.model.Screen
import com.example.tiktokandroid.feed.presentation.view.graphs.RootNavGraph
import com.example.tiktokandroid.theme.TikTokTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun RootView() {

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    // Define the screens where the bottom nav should be visible
    val bottomNavScreens = listOf(
        Screen.Home.route,
        Screen.Friends.route,
        Screen.Upload.route,
        Screen.Notifications.route,
        Screen.Profile.route
    )



    TikTokTheme(darkTheme = false) {

        ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    val currentBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = currentBackStackEntry?.destination?.route
                    if (currentRoute in bottomNavScreens) {
                        BottomNavigationBar(navController = navController)
                    }
                }
            ) { innerPadding ->

                RootNavGraph(navController = navController)

            }
        }
    }
}