package com.example.tiktokandroid.profile.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.core.presentation.model.Post

@Composable
fun ProfileGridView(
    modifier: Modifier = Modifier,
    posts: List<Post>
) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        content = {
            items(posts.size) { index ->
                PostRowItem(
                    modifier = Modifier.padding(all = 3.dp),
                    post = posts[index]
                )
            }
        })


}

@Preview
@Composable
private fun ProfileGridViewPreview() {
    ProfileGridView(
        posts = listOf(
            Post(
                id = "123",
                videoUrl = ""
            ),
            Post(
                id = "123",
                videoUrl = ""
            ),
            Post(
                id = "123",
                videoUrl = ""
            )
        )
    )
}