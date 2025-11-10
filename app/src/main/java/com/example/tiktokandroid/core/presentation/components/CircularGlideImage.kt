package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tiktokandroid.theme.White

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CircularGlideImage(imageUrl: String, size: Int) {

    if (imageUrl.isBlank()) {
        // Default placeholder icon
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Default Profile",
                tint = Color.White,
                modifier = Modifier.size((size * 0.6).dp)
            )
        }
    } else {
        // Profile image
        GlideImage(
            model = imageUrl,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}
