package com.example.tiktokandroid.uploadmedia.presentation.components

import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.RequestOptions

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VideoThumbnail(
    videoUri: Uri,
    modifier: Modifier = Modifier
) {
    GlideImage(
        model = videoUri,
        contentDescription = null,
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop,
        requestBuilderTransform = { requestBuilder ->

            requestBuilder
                .frame(0L)
                .apply(RequestOptions().frame(0L))
        }
    )
}