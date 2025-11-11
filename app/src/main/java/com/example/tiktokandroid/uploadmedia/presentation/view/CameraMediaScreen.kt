package com.example.tiktokandroid.uploadmedia.presentation.view

import android.app.Activity
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tiktokandroid.core.presentation.model.Screen
import com.example.tiktokandroid.uploadmedia.presentation.components.BottomTabLayout
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.CameraMediaViewModel
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.Tabs
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.getCurrentBrightness
import kotlinx.coroutines.launch

/**
 * Created by Puskal Khadka on 4/2/2023.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CameraMediaScreen(
    navController: NavController,
    cameraMediaViewModel: CameraMediaViewModel = hiltViewModel(),
    navigateToPostScreen: (Uri) -> Unit = {}
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    val tabs = Tabs.values().asList()
    val context = LocalContext.current
    val minimumScreenBrightness = 0.25f
    val currentUser = cameraMediaViewModel.currentUser.value

    DisposableEffect(key1 = Unit) {
        val attrs = (context as Activity).window.attributes.apply {
            if (context.getCurrentBrightness() < minimumScreenBrightness) {
                screenBrightness = minimumScreenBrightness
            }
        }
        context.window.attributes = attrs
        onDispose {
            context.window.attributes = attrs.apply {
                screenBrightness = context.getCurrentBrightness()
            }
        }
    }


    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            if (false){
                navController.navigate(Screen.AuthBottomSheet.route)
            }else{
                Box(modifier = Modifier.weight(1f)) {
                    HorizontalPager(
                        state = pagerState,
                        userScrollEnabled = false
                    ) { page ->
                        when (page) {
                            0, 1 -> CameraScreen(
                                navController = navController,
                                viewModel = cameraMediaViewModel,
                                cameraOpenType = tabs[page],
                                navigateToPostScreen = { uri ->
                                    navigateToPostScreen(uri)
                                }
                            )
                            2 -> TemplateScreen(
                                navController = navController,
                                viewModel = cameraMediaViewModel,
                            )
                        }
                    }
                }
                BottomTabLayout(pagerState) {
                    coroutineScope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
            }
        }
    }
}
