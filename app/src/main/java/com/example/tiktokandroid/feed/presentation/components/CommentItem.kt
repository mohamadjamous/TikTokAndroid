package com.example.tiktokandroid.feed.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tiktokandroid.R
import com.example.tiktokandroid.feed.data.model.CommentList
import com.example.tiktokandroid.theme.DarkBlue
import com.example.tiktokandroid.theme.GrayMainColor
import com.example.tiktokandroid.theme.SubTextColor


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CommentItem(item: CommentList.Comment) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val (profileImg, name, comment, createdOn, reply, like, dislike) = createRefs()

        if (item.commentBy.profileImageUrl.isEmpty()) {
            // Default placeholder icon
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Default Profile",
                    tint = Color.White,
                    modifier = Modifier.size((36 * 0.6).dp)
                )
            }
        }else{
            GlideImage(
                model = item.commentBy.profileImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(GrayMainColor)
                    .constrainAs(profileImg) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                ,
            )

        }




        Text(text = item.commentBy.username,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(profileImg.end, margin = 12.dp)
                top.linkTo(profileImg.top)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            })
        Text(text = item.comment ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = DarkBlue,
            modifier = Modifier.constrainAs(comment) {
                start.linkTo(name.start)
                top.linkTo(name.bottom, margin = 5.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            })
        Text(text = item.createdAt, modifier = Modifier.constrainAs(createdOn) {
            start.linkTo(name.start)
            top.linkTo(comment.bottom, margin = 5.dp)
        })

        Text(text = stringResource(id = R.string.reply),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.constrainAs(reply) {
                start.linkTo(createdOn.end, margin = 16.dp)
                top.linkTo(createdOn.top)
                end.linkTo(like.end, margin = 4.dp)
                width = Dimension.fillToConstraints
            })

        Row(
            modifier = Modifier.constrainAs(like) {
                bottom.linkTo(reply.bottom)
                end.linkTo(dislike.start, margin = 24.dp)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_like_outline),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            item.totalLike.takeIf { it != 0L }?.let {
                Text(text = it.toString(), fontSize = 13.sp, color = SubTextColor)
            }

        }

        Row(
            modifier = Modifier.constrainAs(dislike) {
                bottom.linkTo(reply.bottom)
                end.linkTo(parent.end)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dislike_outline),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            // Text(text = "") //dislike not display
        }
    }
    24.dp.Space()
}