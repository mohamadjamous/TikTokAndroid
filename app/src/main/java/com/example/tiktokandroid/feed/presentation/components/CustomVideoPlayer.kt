package com.example.tiktokandroid.feed.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.tiktokandroid.core.presentation.model.Post

@Composable
fun CustomVideoPlayer(
    modifier: Modifier = Modifier,
    url: String
) {
    val context = LocalContext.current
    // Skip ExoPlayer in Preview Mode
    if (LocalInspectionMode.current) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Video Preview",
                color = Color.White
            )
        }
        return
    }

    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    val playerView = remember {
        PlayerView(context).apply {
            player = exoPlayer
        }
    }

    val mediaItem = MediaItem.fromUri(url)
    exoPlayer.setMediaItem(mediaItem)

    DisposableEffect(Unit) {
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { playerView },
        modifier = modifier.fillMaxSize()
    )
}

@Preview
@Composable
private fun CustomVideoPlayerPreview() {
    CustomVideoPlayer(
        url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    )
}