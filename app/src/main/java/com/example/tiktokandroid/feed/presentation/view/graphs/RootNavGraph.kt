package com.example.tiktokandroid.feed.presentation.view.graphs

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.example.tiktokandroid.auth.presentation.components.LoginSignupSwitcher
import com.example.tiktokandroid.auth.presentation.view.SettingsScreen
import com.example.tiktokandroid.auth.presentation.view.email_signup.EmailSignupScreen
import com.example.tiktokandroid.auth.presentation.view.login.EmailPhoneLoginScreen
import com.example.tiktokandroid.auth.presentation.view.phone_signup.PhoneNumberSignupScreen
import com.example.tiktokandroid.auth.presentation.view.screens.DisplayScreen
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.presentation.model.Screen
import com.example.tiktokandroid.explore.presentation.view.screens.ExploreScreen
import com.example.tiktokandroid.feed.presentation.components.CommentListScreen
import com.example.tiktokandroid.feed.presentation.view.screens.FeedScreen
import com.example.tiktokandroid.feed.presentation.viewmodel.FeedViewModel
import com.example.tiktokandroid.notifications.presentation.view.screens.NotificationsScreen
import com.example.tiktokandroid.profile.presentation.view.screens.ProfileScreen
import com.example.tiktokandroid.uploadmedia.presentation.view.CameraMediaScreen
import com.example.tiktokandroid.uploadmedia.presentation.view.PostScreen
import com.example.tiktokandroid.uploadmedia.presentation.view.UploadScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import kotlinx.coroutines.launch

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RootNavGraph(
    navController: NavHostController,
    darkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {

    val graph = navController.createGraph(startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            FeedScreen(
                navController = navController
            )
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
                onDisplayPressed = {
                    navController.navigate(Screen.Display.route)
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Display.route) {
            DisplayScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                darkMode = darkMode,
                onThemeChange = {
                    onThemeChange(it)
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
        startDestination = Screen.Home.route,
    ) {

        composable(route = Screen.Home.route) {
            FeedScreen(
                navController = navController)
        }


        composable(route = Screen.Friends.route) {
            ExploreScreen()
        }


        composable(route = Screen.Upload.route) {

//            UploadScreen(
//                navigateToPostScreen = { videoUri ->
//                    val encodedUri = Uri.encode(videoUri.toString())
//                    navController.navigate(Screen.Post.createRoute(encodedUri))
//                },
//                navigateToProfileScreen = {
//                    navController.navigate(Screen.Profile.route)
//                }
//            )

            CameraMediaScreen(
                navController,
                navigateToPostScreen = { videoUri ->
                    val encodedUri = Uri.encode(videoUri.toString())
                    navController.navigate(Screen.Post.createRoute(encodedUri))
                },
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
                onDisplayPressed = {
                    navController.navigate(Screen.Display.route)
                },
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Display.route) {
            DisplayScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                darkMode = darkMode,
                onThemeChange = {
                    onThemeChange(it)
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


        bottomSheet(
            route = Screen.CommentBottomSheet.route,
            arguments = listOf(navArgument("videoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val videoId = backStackEntry.arguments?.getString("videoId")
            videoId?.let {
                if (navController.currentBackStackEntry != null) { // heck entry validity
                    CommentListScreen(
                        onClickCancel = { navController.navigateUp() },
                        videoId = it,
                        onSuccess = {
//                            feedViewModel.
                        }
                    )
                }
            }
        }

        bottomSheet(
            route = Screen.AuthBottomSheet.route
        )
        {

            AuthBottomSheetContent(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthBottomSheetContent(navController: NavController) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.96f)
            .padding(horizontal = 28.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        // Bottom sheet
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = Color.White,
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxSize()
                .navigationBarsPadding()
        )
        {
            // Sheet content
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                // Top bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                )
                {

                    IconButton(
                        onClick = {
                            scope.launch {
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    LoginSignupSwitcher(
                        navigateToEmailSignup = {
                            navController.navigate(
                                Screen.EmailSignup.route
                            )
                        },
                        navigateToPhoneSignup = { phoneNumber ->
                            navController.navigate(Screen.PhoneNumberSignup.createRoute(phoneNumber))
                        },
                        navigateToEmailPhoneLogin = {
                            navController.navigate(Screen.EmailPhoneLogin.route)
                        },
                    )
                }


            }
        }

    }
}