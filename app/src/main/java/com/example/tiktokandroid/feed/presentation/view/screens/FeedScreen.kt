package com.example.tiktokandroid.feed.presentation.view.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.tiktokandroid.feed.presentation.viewmodel.FeedViewModel

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = FeedViewModel()
) {

    val videos = viewModel.videos.collectAsState()

    
}