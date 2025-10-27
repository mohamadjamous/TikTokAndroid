package com.example.tiktokandroid.feed.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.tiktokandroid.core.presentation.components.BottomNavigationBar
import com.example.tiktokandroid.core.presentation.view.Screen
import com.example.tiktokandroid.explore.presentation.view.screens.ExploreScreen
import com.example.tiktokandroid.feed.presentation.view.graphs.RootNavGraph
import com.example.tiktokandroid.feed.presentation.view.screens.FeedScreen
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokAndroidTheme
import com.example.tiktokandroid.notifications.presentation.view.screens.NotificationsScreen
import com.example.tiktokandroid.profile.presentation.view.screens.ProfileScreen
import com.example.tiktokandroid.uploadmedia.presentation.view.UploadScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        lateinit var instance: MainActivity
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        instance = this


        setContent {
            TikTokAndroidTheme {

                val navController = rememberNavController()

                // Define the screens where the bottom nav should be visible
                val bottomNavScreens = listOf(
                    Screen.Home.rout,
                    Screen.Friends.rout,
                    Screen.Upload.rout,
                    Screen.Notifications.rout,
                    Screen.Profile.rout
                )

                Scaffold(modifier = Modifier.fillMaxSize(),
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
}