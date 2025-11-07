package com.example.tiktokandroid.core.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.tiktokandroid.core.presentation.model.Screen
import com.example.tiktokandroid.feed.presentation.view.graphs.homeNavGraph

/**
 * Created by Puskal Khadka on 3/14/2023.
 */

@Composable
fun AppNavHost( navController: NavHostController,
                modifier: Modifier = Modifier,
                startDestination: String = Screen.Home.route
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeNavGraph(navController)
//        commentListingNavGraph(navController)
//        creatorProfileNavGraph(navController)
//        inboxNavGraph(navController)
//        authenticationNavGraph(navController)
//        loginEmailPhoneNavGraph(navController)
//        friendsNavGraph(navController)
//        myProfileNavGraph(navController)
//        settingNavGraph(navController)
//        cameraMediaNavGraph(navController)
    }

}