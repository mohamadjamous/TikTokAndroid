package com.example.tiktokandroid.feed.presentation.view.screens

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.tiktokandroid.feed.presentation.components.FeedView
import com.example.tiktokandroid.feed.presentation.viewmodel.FeedViewModel

import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner


@OptIn(UnstableApi::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val videos by viewModel.videos.collectAsState()
    val pagerState = rememberPagerState(pageCount = { videos.size })
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Cache setup
    val cacheDir = File(context.cacheDir, "media_cache")
    val cache =
        remember { SimpleCache(cacheDir, LeastRecentlyUsedCacheEvictor(100L * 1024 * 1024)) }
    val httpDataSourceFactory = DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true)
    val cacheDataSourceFactory = CacheDataSource.Factory()
        .setCache(cache)
        .setUpstreamDataSourceFactory(DefaultDataSource.Factory(context, httpDataSourceFactory))
        .setFlags(CacheDataSource.FLAG_BLOCK_ON_CACHE)

    val loadControl = DefaultLoadControl.Builder()
        .setBufferDurationsMs(500, 5000, 250, 500)
        .build()


    // Create first player
    val activePlayers = remember { mutableMapOf<Int, ExoPlayer>() }
    if (videos.isNotEmpty() && activePlayers[0] == null) {
        val firstPlayer = ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory))
            .setLoadControl(loadControl)
            .build()
            .apply {
                setMediaItem(MediaItem.fromUri(videos[0].videoUrl.toUri()))
                prepare()
                playWhenReady = true
            }
        activePlayers[0] = firstPlayer
    }


    // Preload visible range
    val visibleRange =
        maxOf(0, pagerState.currentPage - 1)..minOf(videos.lastIndex, pagerState.currentPage + 1)
    LaunchedEffect(pagerState.currentPage, videos) {
        // Release players outside visible range
        activePlayers.keys.toList().forEach { index ->
            if (index !in visibleRange) {
                activePlayers[index]?.release()
                activePlayers.remove(index)
            }
        }

        // Ensure players exist for visible range
        visibleRange.forEach { index ->
            if (activePlayers[index] == null) {
                val post = videos[index]
                val player = ExoPlayer.Builder(context)
                    .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory))
                    .setLoadControl(loadControl)
                    .build()
                    .apply {
                        setMediaItem(MediaItem.fromUri(post.videoUrl.toUri()))
                        prepare()
                        playWhenReady = index == pagerState.currentPage
                    }
                activePlayers[index] = player
            } else if (index == pagerState.currentPage) {
                activePlayers[index]?.playWhenReady = true
            } else {
                activePlayers[index]?.playWhenReady = false
            }
        }
    }


    VerticalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 55.dp)
    ) { page ->
        val player = activePlayers[page]
        player?.let { FeedView(post = videos[page], player = it) }
    }


    DisposableEffect(Unit) {
        onDispose {
            activePlayers.values.forEach { it.release() }
            activePlayers.clear()
        }
    }


    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    // App is backgrounded
                    activePlayers.values.forEach { it.pause() }
                }

                Lifecycle.Event.ON_RESUME -> {
                    // App is foregrounded
                    activePlayers[pagerState.currentPage]?.playWhenReady = true
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


}