package com.example.tiktokandroid.feed.presentation.components

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

/*

    Things to add:

        add pause play buttons on click
        add circular progress bar when video is loading

 */

@OptIn(UnstableApi::class)
@Composable
fun CustomVideoPlayer(
    modifier: Modifier = Modifier,
    exoPlayer: ExoPlayer
) {

    val context = LocalContext.current

    // Skip player creation for preview mode
    if (LocalInspectionMode.current) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Text("Video Preview", color = Color.White)
        }
        return
    }

    // Configure the playerView to match AVPlayerViewController settings
    val playerView = remember {
        PlayerView(context).apply {
            useController = false // no native playback controls
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM // fill like .resizeAspectFill
            player = exoPlayer
        }
    }

    // Display playerView inside Compose
    AndroidView(
        factory = { playerView },
        modifier = modifier.fillMaxSize()
    )
}

