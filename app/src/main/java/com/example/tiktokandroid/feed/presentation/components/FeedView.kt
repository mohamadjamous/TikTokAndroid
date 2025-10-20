package com.example.tiktokandroid.feed.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.tiktokandroid.core.presentation.model.Post

@Composable
fun FeedView(
    modifier: Modifier = Modifier,
    post: Post
) {

    val context = LocalContext.current
    val sharedPlayer = remember { ExoPlayer.Builder(context).build() }

    LaunchedEffect(post.videoUrl) {
        sharedPlayer.setMediaItem(MediaItem.fromUri(post.videoUrl))
        sharedPlayer.prepare()
        sharedPlayer.playWhenReady = true
    }

    Box(modifier = modifier.fillMaxSize()) {

        CustomVideoPlayer(
            exoPlayer = sharedPlayer
        )

        // Optional overlay (UI on top of video)
        VideoOverlay(
            modifier = Modifier.fillMaxSize(),
            onTogglePlay = {
                if (sharedPlayer.isPlaying) sharedPlayer.pause()
                else sharedPlayer.play()
            }
        )

    }
}



