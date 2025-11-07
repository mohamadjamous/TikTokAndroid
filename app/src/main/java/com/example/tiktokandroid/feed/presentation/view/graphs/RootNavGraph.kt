package com.example.tiktokandroid.feed.presentation.view.graphs

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.example.tiktokandroid.auth.presentation.view.SettingsScreen
import com.example.tiktokandroid.auth.presentation.view.email_signup.EmailSignupScreen
import com.example.tiktokandroid.auth.presentation.view.login.EmailPhoneLoginScreen
import com.example.tiktokandroid.auth.presentation.view.phone_signup.PhoneNumberSignupScreen
import com.example.tiktokandroid.core.presentation.model.Screen
import com.example.tiktokandroid.explore.presentation.view.screens.ExploreScreen
import com.example.tiktokandroid.feed.presentation.view.screens.FeedScreen
import com.example.tiktokandroid.notifications.presentation.view.screens.NotificationsScreen
import com.example.tiktokandroid.profile.presentation.view.screens.ProfileScreen
import com.example.tiktokandroid.uploadmedia.presentation.view.PostScreen
import com.example.tiktokandroid.uploadmedia.presentation.view.UploadScreen

@Composable
fun RootNavGraph(navController: NavHostController) {

    val graph =
        navController.createGraph(startDestination = Screen.Home.route) {
            composable(route = Screen.Home.route) {
                FeedScreen()
            }
            composable(route = Screen.Friends.route) {
                ExploreScreen()
            }
            composable(route = Screen.Upload.route) {
                UploadScreen(
                    navigateToPostScreen = { videoUri ->
                        val encodedUri = Uri.encode(videoUri.toString())
                        navController.navigate(Screen.Post.createRoute(encodedUri))
                    },
                    navigateToProfileScreen = {
                        navController.navigate(Screen.Profile.route)
                    }
                )
            }
            composable(route = Screen.Notifications.route) {
                NotificationsScreen()
            }
            composable(route = Screen.Profile.route) {
                ProfileScreen(
                    navigateToEmailSignup = {
                        navController.navigate(
                            Screen.EmailSignup.route
                        )
                    },
                    navigateToSettings = {
                        navController.navigate(
                            Screen.Settings.route
                        )
                    },
                    navigateToPhoneSignup = { phoneNumber ->
                        navController.navigate(Screen.PhoneNumberSignup.createRoute(phoneNumber))
                    },
                    navigateToEmailPhoneLogin = {
                        navController.navigate(Screen.EmailPhoneLogin.route)
                    },
                    onClickVideo = { post, index ->
//                        navController.navigate("$CREATOR_VIDEO_ROUTE/${viewModel.userId}/$index")
                    }
                )
            }

            composable(route = Screen.EmailSignup.route) {
                EmailSignupScreen(
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = Screen.Settings.route) {
                SettingsScreen(
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.PhoneNumberSignup.route,
                arguments = listOf(navArgument("phoneNumber") { type = NavType.StringType })
            ) { backStackEntry ->
                val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
                PhoneNumberSignupScreen(
                    phoneNumber = phoneNumber,
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = Screen.EmailPhoneLogin.route) {
                EmailPhoneLoginScreen(
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.Post.route,
                arguments = listOf(navArgument("videoUri") { type = NavType.StringType })
            ) { backStackEntry ->
                val videoUriString = backStackEntry.arguments?.getString("videoUri")
                val videoUri = videoUriString?.let { Uri.parse(Uri.decode(it)) }

                PostScreen(
                    onBackPressed = {
                        navController.popBackStack()
                    },
                    videoUri = videoUri
                )
            }


        }


    NavHost(
        navController = navController,
        graph = graph,
    )
}