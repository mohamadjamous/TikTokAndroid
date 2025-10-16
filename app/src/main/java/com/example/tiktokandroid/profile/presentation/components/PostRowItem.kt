package com.example.tiktokandroid.profile.presentation.components

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.core.presentation.model.Post

@Composable
fun PostRowItem(modifier: Modifier = Modifier, post: Post) {

    val width = (Resources.getSystem().displayMetrics.widthPixels / 3f) - 2f

    Box(
        modifier = modifier
            .width(width.dp)
            .height(160.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = post.id,
            color = Color.White
        )
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