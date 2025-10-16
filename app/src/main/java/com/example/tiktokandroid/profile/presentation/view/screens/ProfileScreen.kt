package com.example.tiktokandroid.profile.presentation.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.profile.presentation.components.PostRowItem
import com.example.tiktokandroid.profile.presentation.components.ProfileGridView

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {

    val posts = listOf(
        Post(id = "123", videoUrl = "123"),
        Post(id = "124", videoUrl = "123"),
        Post(id = "125", videoUrl = "123"),
        Post(id = "126", videoUrl = "123"),
        Post(id = "127", videoUrl = "123"),
        Post(id = "123", videoUrl = "123"),
        Post(id = "124", videoUrl = "123"),
        Post(id = "125", videoUrl = "123"),
        Post(id = "126", videoUrl = "123"),
        Post(id = "127", videoUrl = "123"),
        Post(id = "123", videoUrl = "123"),
        Post(id = "124", videoUrl = "123"),
        Post(id = "125", videoUrl = "123"),
        Post(id = "126", videoUrl = "123"),
        Post(id = "127", videoUrl = "123"),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 55.dp),
        contentPadding = PaddingValues(bottom = 50.dp)
    ) {

        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp)
                )

                ProfileHeaderView(
                    modifier = Modifier.padding(top = 30.dp),
                    imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
                    username = "@lwis.hamilton"
                )
            }
        }

        items(posts.size) { index ->
            PostRowItem(
                modifier = Modifier.padding(2.dp),
                post = posts[index]
            )
        }
    }
}



@Composable
@Preview(showSystemUi = true)
fun ProfileViewPreview() {
    ProfileScreen()
}