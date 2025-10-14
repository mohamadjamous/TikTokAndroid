package com.example.tiktokandroid.feed.presentation.view.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tiktokandroid.feed.presentation.view.screens.FeedScreen

@Composable
fun RootNavGraph(navController: NavHostController) {


    NavHost (
        navController = navController,
        startDestination = "feed"
    ){

        composable("feed"){
            FeedScreen()
        }


    }

}