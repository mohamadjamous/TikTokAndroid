package com.example.tiktokandroid.feed.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.R
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.theme.White

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FooterUi(
    modifier: Modifier,
    item: Post,
    showUploadDate: Boolean,
    onClickAudio: (Post) -> Unit,
    onClickUser: (userId: Long) -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Bottom) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
//            onClickUser(item.authorDetails.userId)
            onClickUser(0)
        }) {
            Text(
//                text = item.authorDetails.username,
                text = item.username,
                style = MaterialTheme.typography.bodyMedium,
                color = White
            )
            if (showUploadDate) {
                Text(
                    text = " . ${item.createdAt} ago",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
        5.dp.Space()
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth(0.85f),
            color = White
        )
        10.dp.Space()
        val audioInfo: String = item.audioModel?.run {
            "Original sound - ${audioAuthor.username} - ${audioAuthor.fullName}"
        }
            ?: item.run { "Original sound - ${item.authorDetails.username} - ${item.authorDetails.fullName}" }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable {
                onClickAudio(item)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_music_note),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = audioInfo,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .basicMarquee(),
                color = White
            )
        }
    }
}