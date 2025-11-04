package com.example.tiktokandroid.profile.presentation.components

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.FileUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tiktokandroid.core.presentation.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Puskal Khadka on 3/25/2023.
 */

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PostRowItem(
    modifier: Modifier = Modifier,
    post: Post,
    index: Int = 0,
    onClickVideo: (Post, Int) -> Unit = { _, _ -> }
) {
    val width = (Resources.getSystem().displayMetrics.widthPixels / 3f) - 2f
    var thumbnail: Bitmap? by remember { mutableStateOf(null) }

    Box(
        modifier = modifier
            .width(width.dp)
            .height(160.dp)
            .clickable { onClickVideo(post, index) }
    ) {
        // Thumbnail image
        GlideImage(
            model = thumbnail ?: post.videoUrl, // fallback to URL if you have one
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.5f)
                            ),
                            startY = 300f
                        )
                    )
                },
            contentScale = ContentScale.Crop
        )

        // Load thumbnail bitmap (optional)
        LaunchedEffect(post.videoUrl) {
            withContext(Dispatchers.IO) {
                // Example if you want to extract a thumbnail manually later
                // val bitmap = FileUtils.extractThumbnailFromVideo(post.videoUrl)
                // withContext(Dispatchers.Main) { thumbnail = bitmap }
            }
        }

        // Bottom row with play icon and view count (or likes)
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = Color.White
            )
            Text(
                text = (123 ?: 0).toString(),
                color = Color.White,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
private fun PostRowItemPreview() {
    PostRowItem(
        post = Post(
            id = "123",
            videoUrl = ""
        )
    )
}
//
//@Composable
//fun VideoGridItem(item: Post, index: Int, onClickVideo: (Post, Int) -> Unit) {
//    Box(
//        modifier = Modifier
//            .height(160.dp)
//            .clickable {
//                onClickVideo(item, index)
//            }
//    ) {
//        var thumbnail: Bitmap? by remember {
//            mutableStateOf(null)
//        }
//        GlideImage(
//            model = thumbnail,
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Gray)
//                .drawWithContent {
//                    drawContent()
//                    drawRect(
//                        brush = Brush.verticalGradient(
//                            listOf(
//                                Color.Transparent,
//                                Color.Black.copy(alpha = 0.5f)
//                            ), startY = 300f
//                        )
//                    )
//                },
//            contentScale = ContentScale.Crop
//        )
//        LaunchedEffect(key1 = true) {
//            withContext(Dispatchers.IO) {
////                val bitmap = FileUtils.extractThumbnail(
////                    assets.openFd("videos/${item.videoLink}"), 1
////                )
////                withContext(Dispatchers.Main) {
////                    thumbnail = bitmap
////
////                }
//            }
//        }
//
//        Row(
//            modifier = Modifier
//                .align(Alignment.BottomStart)
//                .padding(4.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(2.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Default.PlayArrow,
//                contentDescription = null,
//                modifier = Modifier.size(12.dp),
//                tint = Color.Unspecified
//            )
//            Text(
//                text = "123",
//                color = Color.White,
//                style = MaterialTheme.typography.labelMedium
//            )
//        }
//
//    }
//}