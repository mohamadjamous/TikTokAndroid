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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiktokandroid.core.presentation.model.Post

@Composable
fun VideoOverlay(
    modifier: Modifier = Modifier,
    onTogglePlay: () -> Unit) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { onTogglePlay() }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 90.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "carlos.name",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )

                    Text(
                        text = "Rocket ship prepare for take off",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Profile Circle
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Gray, CircleShape)
                    )

                    // Like Button
                    IconTextButton(
                        icon = Icons.Filled.Favorite,
                        count = 27
                    )

                    // Comment Button
                    IconTextButton(
                        icon = Icons.Filled.ChatBubble,
                        count = 27
                    )

                    // Bookmark Button
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            imageVector = Icons.Filled.Bookmark,
                            contentDescription = "Bookmark",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // Share Button
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun VideoOverlayPreview() {
    VideoOverlay(
        onTogglePlay = {

        }
    )
}