package com.example.tiktokandroid.profile.presentation.components.tabs

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.profile.presentation.components.VideoGrid
import com.example.tiktokandroid.profile.presentation.viewmodel.ProfileViewModel

@Composable
fun SavedVideoTab(
    viewModel: ProfileViewModel,
    scrollState: ScrollState,
    onClickVideo: (video: Post, index: Int) -> Unit
) {

    val savedVideos by viewModel.savedVideos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchSavedVideos()
    }

    VideoGrid(
        scrollState = scrollState,
        videoList = savedVideos,
        onClickVideo = onClickVideo
    )
}