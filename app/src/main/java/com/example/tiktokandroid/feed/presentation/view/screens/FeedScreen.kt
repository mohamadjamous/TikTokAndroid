package com.example.tiktokandroid.feed.presentation.view.screens

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.tiktokandroid.feed.presentation.viewmodel.FeedViewModel

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
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
import java.io.File
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.database.StandaloneDatabaseProvider
import com.example.tiktokandroid.auth.data.model.AuthUiState
import com.example.tiktokandroid.core.presentation.components.CustomLoadingView
import com.example.tiktokandroid.core.presentation.components.LoadingEffect
import com.example.tiktokandroid.feed.data.model.FeedUiState
import com.example.tiktokandroid.feed.presentation.components.FeedView


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

    var loading by remember { mutableStateOf(false) }
    val state = viewModel.uiState.collectAsState().value
    val loadMoreVideos = viewModel.loadMoreVideos.value

    LaunchedEffect(state) {
        when (state) {
            is FeedUiState.Idle -> { /* Show initial screen */
            }

            is FeedUiState.Loading -> {
                loading = true
            }

            is FeedUiState.Success -> {
                loading = false


            }

            is FeedUiState.Error -> {
                loading = false
            }
        }
    }

    // Cache setup


    val loadControl = DefaultLoadControl.Builder()
        .setBufferDurationsMs(500, 5000, 250, 500)
        .build()


    // Create first player
    val activePlayers = remember { mutableMapOf<Int, ExoPlayer>() }
    if (videos.isNotEmpty() && activePlayers[0] == null) {
        val firstPlayer = ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(viewModel.cacheDataSourceFactory!!))
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
                    .setMediaSourceFactory(DefaultMediaSourceFactory(viewModel.cacheDataSourceFactory!!))
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


    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Column(modifier = Modifier.fillMaxSize()
            .padding(bottom = 55.dp)) {

            VerticalPager(
                state = pagerState,
                modifier = modifier
                    .fillMaxSize()
                    .weight(5f)
            ) { page ->
                val player = activePlayers[page]
                player?.let { FeedView(post = videos[page], player = it) }

                // Load more when reaching the last item of current batch
                if (page == videos.lastIndex - 0) { // trigger 1 item before end
                    viewModel.fetchMorePosts()
                }
            }

            if (loadMoreVideos) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 50.dp)
                        .background(Color.Black)
                        .weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LoadingEffect()
                }
            }
        }


        if (loading) {
            CustomLoadingView()
        }

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

@OptIn(androidx.media3.common.util.UnstableApi::class)
object MediaCacheSingleton {
    private var simpleCache: SimpleCache? = null

    fun getInstance(context: Context): SimpleCache {
        if (simpleCache == null) {
            val cacheDir = File(context.cacheDir, "media_cache")
            val evictor = LeastRecentlyUsedCacheEvictor(50L * 1024L * 1024L) // 50 MB
            val databaseProvider = StandaloneDatabaseProvider(context)
            simpleCache = SimpleCache(cacheDir, evictor, databaseProvider)
        }
        return simpleCache!!
    }
}
