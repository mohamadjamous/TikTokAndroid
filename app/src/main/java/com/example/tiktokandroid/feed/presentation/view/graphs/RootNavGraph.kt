package com.example.tiktokandroid.feed.presentation.view.graphs

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
import com.example.tiktokandroid.core.presentation.view.Screen
import com.example.tiktokandroid.explore.presentation.view.screens.ExploreScreen
import com.example.tiktokandroid.feed.presentation.view.screens.FeedScreen
import com.example.tiktokandroid.notifications.presentation.view.screens.NotificationsScreen
import com.example.tiktokandroid.profile.presentation.view.screens.ProfileScreen
import com.example.tiktokandroid.uploadmedia.presentation.view.PostScreen
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
                UploadScreen(
                    navigateToPostScreen = {
                      navController.navigate(
                          route = Screen.Post.rout
                      )
                    }
                )
            }
            composable(route = Screen.Notifications.rout) {
                NotificationsScreen()
            }
            composable(route = Screen.Profile.rout) {
                ProfileScreen(
                    navigateToEmailSignup = {
                        navController.navigate(
                            Screen.EmailSignup.rout
                        )
                    },
                    navigateToSettings = {
                        navController.navigate(
                            Screen.Settings.rout
                        )
                    },
                    navigateToPhoneSignup = { phoneNumber ->
                        navController.navigate(Screen.PhoneNumberSignup.createRoute(phoneNumber))
                    },
                    navigateToEmailPhoneLogin = {
                        navController.navigate(Screen.EmailPhoneLogin.rout)
                    }
                )
            }

            composable(route = Screen.EmailSignup.rout) {
                EmailSignupScreen(
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = Screen.Settings.rout) {
                SettingsScreen(
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.PhoneNumberSignup.rout,
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

            composable(route = Screen.EmailPhoneLogin.rout) {
                EmailPhoneLoginScreen(
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }

            composable(route = Screen.Post.rout) {
                PostScreen (
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }

        }


    NavHost(
        navController = navController,
        graph = graph,
    )
}