package com.example.tiktokandroid.feed.presentation.view.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.explore.presentation.components.ExploreListItemView
import com.example.tiktokandroid.feed.presentation.components.FeedView
import com.example.tiktokandroid.feed.presentation.viewmodel.FeedViewModel

import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState


@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = FeedViewModel()
) {

    val videos = viewModel.videos.collectAsState().value
    val pagerState = rememberPagerState(
        pageCount = { videos.size }
    )

    // Full-screen pager for vertical scrolling
    VerticalPager(
        modifier = modifier.fillMaxSize(),
        state = pagerState
    ) { page ->

        val post = videos[page]

        FeedView(
            post = post,
            onTogglePlay = {
                // your play/pause logic here
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}