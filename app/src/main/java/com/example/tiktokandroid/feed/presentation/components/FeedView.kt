package com.example.tiktokandroid.feed.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.exoplayer.ExoPlayer
import com.example.tiktokandroid.core.presentation.model.Post

@Composable
fun FeedView(
    modifier: Modifier = Modifier,
    post: Post,
    player: ExoPlayer
) {

    Box(modifier = modifier.fillMaxSize()) {
        CustomVideoPlayer(exoPlayer = player)

        VideoOverlay(
            modifier = Modifier.fillMaxSize(),
            onTogglePlay = {
                if (player.isPlaying) player.pause() else player.play()
            }
        )
    }
}

