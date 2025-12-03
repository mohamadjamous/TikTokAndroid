package com.example.tiktokandroid.core.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tiktokandroid.core.presentation.components.BottomNavigationBar
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.presentation.model.Screen
import com.example.tiktokandroid.feed.presentation.view.graphs.RootNavGraph
import com.example.tiktokandroid.theme.TikTokTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun RootView(
    onPrefetch: (index: Int, list: List<Post>) -> Unit
) {

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    // Define the screens where the bottom nav should be visible
    val bottomNavScreens = listOf(
        Screen.Home.route,
        Screen.Friends.route,
        Screen.Notifications.route,
        Screen.Profile.route
    )

    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntryAsState?.destination

    val darkMode = when (currentDestination?.route) {
        Screen.Home.route,
        Screen.Upload.route,
        null -> true
        else -> false
    }

    TikTokTheme(darkTheme = darkMode) {

        SetupSystemUi(rememberSystemUiController(), MaterialTheme.colorScheme.background)

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

                RootNavGraph(navController = navController,
                    onPrefetch = onPrefetch)

            }
        }
    }
}
@Composable
fun SetupSystemUi(
    systemUiController: SystemUiController,
    systemBarColor: Color
) {
    SideEffect {
        systemUiController.setSystemBarsColor(color = systemBarColor)
    }
}
