package com.example.tiktokandroid.profile.presentation.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.core.presentation.components.CircularGlideImage

@Composable
fun ProfileHeaderView(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    username: String = "",
    following: Int = 0,
    follower: Int = 0,
    likes: Int = 0) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {

        CircularGlideImage(imageUrl = imageUrl, size = 115)

        Text(
            text = username,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )


    }


}


@Composable
@Preview(showSystemUi = true)
fun ProfileHeaderViewPreview(){
    ProfileHeaderView(
        username = "username default",
        imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
    )
}