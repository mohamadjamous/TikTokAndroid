package com.example.tiktokandroid.notifications.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.core.presentation.components.CircularGlideImage
import com.example.tiktokandroid.core.presentation.components.SquareGlideImage
import com.example.tiktokandroid.notifications.data.model.Notification
import com.example.tiktokandroid.notifications.domain.viewmodel.NotificationsViewModel

@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    notification: Notification,
    viewModel: NotificationsViewModel = NotificationsViewModel()
) {


    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically) {

        CircularGlideImage(imageUrl = notification.userPhotoUrl, size = 30)

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = notification.username,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall
            )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = notification.description,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = viewModel.timeAgoFromNow(notification.time),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.width(8.dp))

        SquareGlideImage(imageUrl = notification.userPhotoUrl, size = 60)

    }

}


@Preview
@Composable
private fun NotificationItemPreview() {
    NotificationItem(notification = Notification(
        id = "123",
        username = "max.verstappen",
        userPhotoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
        description = "liked one of your posts",
        time = 10000,
        photoUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
    ))
}