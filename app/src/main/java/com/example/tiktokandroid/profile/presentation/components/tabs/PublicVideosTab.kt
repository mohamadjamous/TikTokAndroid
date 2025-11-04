package com.example.tiktokandroid.profile.presentation.components.tabs

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.profile.presentation.components.VideoGrid
import com.example.tiktokandroid.profile.presentation.viewmodel.ProfileViewModel

/**
 * Created by Puskal Khadka on 3/25/2023.
 */

@Composable
fun PublicVideoTab(
    viewModel: ProfileViewModel,
    scrollState: ScrollState,
    onClickVideo: (video: Post, index: Int) -> Unit
) {
    val publicVideos by viewModel.posts.collectAsState()

    VideoGrid(
        scrollState = scrollState,
        videoList = publicVideos,
        onClickVideo = onClickVideo
    )
}