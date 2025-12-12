package com.example.tiktokandroid.feed.presentation.view.screens

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.example.tiktokandroid.core.presentation.components.CustomLoadingView
import com.example.tiktokandroid.core.presentation.components.LoadingEffect
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.presentation.model.Screen
import com.example.tiktokandroid.core.service.VideoPrefetchService
import com.example.tiktokandroid.feed.data.model.FeedUiState
import com.example.tiktokandroid.feed.presentation.components.TikTokVerticalVideoPager
import com.example.tiktokandroid.feed.presentation.viewmodel.FeedViewModel
import com.example.tiktokandroid.theme.DarkBlue
import com.example.tiktokandroid.theme.DarkPink

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ForYouTabScreen(
    navController: NavController,
    viewModel: FeedViewModel = hiltViewModel(),
    onPrefetch: (index: Int, list: List<Post>) -> Unit
) {
    val videos by viewModel.videos.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    var showInitialLoading by remember { mutableStateOf(true) }
    val currentUser = viewModel.currentUser.value
    lateinit var prefetchService: VideoPrefetchService

    // Handle UI state
    LaunchedEffect(uiState) {
        when (uiState) {
            is FeedUiState.Idle -> Unit
            is FeedUiState.Loading -> showInitialLoading = videos.isEmpty()
            is FeedUiState.Success, is FeedUiState.Error -> showInitialLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 84.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(DarkPink, DarkBlue)
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        TikTokVerticalVideoPager(
            videos = videos,
            onclickComment = { videoId, onNewComment ->
                navController.navigate(Screen.CommentBottomSheet.createRoute(videoId))
//                viewModel.setOnNewComment(videoId, onNewComment)
            },
            onClickLike = { videoId, liked ->
                viewModel.updateLikeState(videoId, liked)
            },
            onClickFavourite1 = { id, saved ->
                viewModel.updateSavedState(id, saved)
            },
            onClickAudio = {},
            onClickUser = {
//                    userId -> navController.navigate("$CREATOR_PROFILE_ROUTE/$userId")
            },
            onPageChanged = { index ->

                // Fetch more when reaching near the end (last 2 items)

                println("CurrentIndex: $index")
                if (index >= videos.size - 2) {
//                    onPrefetch(index, videos)
                }
            },
            currentUser = currentUser,
            viewModel = viewModel
        )

        if (showInitialLoading) {
            LoadingEffect()
        }
    }
}