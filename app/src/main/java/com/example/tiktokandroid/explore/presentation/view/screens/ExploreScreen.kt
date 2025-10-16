package com.example.tiktokandroid.explore.presentation.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.explore.data.model.ExploreItem
import com.example.tiktokandroid.explore.presentation.components.ExploreListItemView
import com.example.tiktokandroid.notifications.data.model.Notification

@Composable
fun ExploreScreen(modifier: Modifier = Modifier) {

    val friends = listOf(
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        ),
        ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg"
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 55.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Explore",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 55.dp)
                .padding(top = 20.dp, start = 20.dp)

        ) {
            items(friends.size) { index ->
                ExploreListItemView(
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    exploreItem = friends[index]
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ExploreScreenPreview() {
    ExploreScreen()
}