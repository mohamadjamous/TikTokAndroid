package com.example.tiktokandroid.feed.presentation.view.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.tiktokandroid.feed.presentation.components.FeedView
import com.example.tiktokandroid.feed.presentation.viewmodel.FeedViewModel

import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File


@OptIn(UnstableApi::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {

    val videos = viewModel.videos.collectAsState().value
    val pagerState = rememberPagerState(pageCount = { videos.size })
    val context = LocalContext.current

    val loadControl = DefaultLoadControl.Builder()
        .setBufferDurationsMs(500, 5000, 250, 500)
        .build()

    val cacheDir = File(context.cacheDir, "media_cache")
    val cache = remember { SimpleCache(cacheDir, LeastRecentlyUsedCacheEvictor(100L * 1024 * 1024)) }

    val httpDataSourceFactory = DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true)

    val cacheDataSourceFactory = CacheDataSource.Factory()
        .setCache(cache)
        .setUpstreamDataSourceFactory(DefaultDataSource.Factory(context, httpDataSourceFactory))
        .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR or CacheDataSource.FLAG_BLOCK_ON_CACHE)

    val player = remember {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory))
            .setLoadControl(loadControl)
            .build()
    }

    // Track current page and update player
    LaunchedEffect(pagerState.currentPage) {
        val post = videos.getOrNull(pagerState.currentPage) ?: return@LaunchedEffect
        val mediaItem = MediaItem.fromUri(post.videoUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true

        // Preload next video for smoothness
//        val nextIndex = pagerState.currentPage + 1
//        if (nextIndex < videos.size) {
//            val nextItem = MediaItem.fromUri(videos[nextIndex].videoUrl)
//            player.addMediaItem(nextItem)
//        }
    }

    VerticalPager(
        modifier = modifier.fillMaxSize()
            .padding(bottom = 55.dp),
        state = pagerState
    ) { page ->
        val post = videos[page]
        FeedView(post = post, player = player)
    }

    DisposableEffect(Unit) { onDispose { player.release() } }
}