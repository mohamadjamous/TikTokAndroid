package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SquareGlideImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    size: Int
) {
    GlideImage(
        model = imageUrl,
        contentDescription = "Square Image",
        modifier = modifier
            .size(size.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop
    )
}