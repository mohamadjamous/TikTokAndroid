package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CircularGlideImage(imageUrl: String, size: Int) {

    GlideImage(
        model = imageUrl,
        contentDescription = "Circular Image",
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )

}
