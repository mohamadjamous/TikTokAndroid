package com.example.tiktokandroid.notifications.presentation.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.notifications.data.Notification
import com.example.tiktokandroid.notifications.presentation.components.NotificationItem
import com.example.tiktokandroid.profile.presentation.components.PostRowItem
import com.example.tiktokandroid.profile.presentation.view.screens.ProfileHeaderView

@Composable
fun NotificationsScreen(modifier: Modifier = Modifier) {

    val notifications = listOf(
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ), Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        Notification(
            id = "123",
            username = "max.verstappen",
            userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
            description = "liked one of your posts",
            time = 10000,
            photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        )

    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 55.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Notifications",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 55.dp)
                .padding(top = 20.dp, start = 20.dp)

        ){
            items(notifications.size) { index ->
                NotificationItem(
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    notification = notifications[index]
                )
            }
        }
    }






}

@Preview(showSystemUi = true)
@Composable
private fun NotificationsScreenPreview() {
    NotificationsScreen()
}