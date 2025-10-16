package com.example.tiktokandroid.explore.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.example.tiktokandroid.explore.data.model.ExploreItem
import com.example.tiktokandroid.feed.presentation.view.theme.Typography

@Composable
fun ExploreListItemView(
    modifier: Modifier = Modifier,
    exploreItem: ExploreItem
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        CircularGlideImage(
            imageUrl = exploreItem.userProfileUrl,
            size = 47
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = exploreItem.userName,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = exploreItem.userFullName,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

}


@Preview
@Composable
private fun ExploreListItemPreview() {
    ExploreListItemView(
        exploreItem = ExploreItem(
            id = "123",
            userFullName = "full name",
            userName = "full_name",
            userProfileUrl = "https"
        )
    )
}