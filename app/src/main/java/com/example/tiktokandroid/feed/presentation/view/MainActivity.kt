package com.example.tiktokandroid.feed.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.tiktokandroid.core.presentation.components.BottomNavigationBar
import com.example.tiktokandroid.feed.presentation.view.graphs.RootNavGraph
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokAndroidTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            TikTokAndroidTheme {

                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                    ) { innerPadding ->

                    RootNavGraph(navController = navController)

                }
            }
        }
    }
}