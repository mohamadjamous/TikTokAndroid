package com.example.tiktokandroid.feed.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tiktokandroid.R
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.utils.IntentUtils.share
import com.example.tiktokandroid.theme.White

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SideItems(
    modifier: Modifier,
    post: Post,
    doubleTabState: Triple<Offset, Boolean, Float>,
    onclickComment: (videoId: String) -> Unit,
    onClickUser: (userId: Long) -> Unit,
    onClickShare: (() -> Unit)? = null,
    onClickLike: (Boolean) -> Unit,
    onClickFavourite: (isFav: Boolean) -> Unit
) {

    val context = LocalContext.current
    var isSaved by remember {
        mutableStateOf(post.currentViewerInteraction.isAddedToFavourite)
    }

    var isLiked by remember {
        mutableStateOf(post.currentViewerInteraction.isLikedByYou)
    }

    LaunchedEffect(key1 = doubleTabState) {
        if (doubleTabState.first != Offset.Unspecified && doubleTabState.second) {
            isLiked = doubleTabState.second
        }
    }

    LaunchedEffect(key1 = doubleTabState) {
        if (doubleTabState.first != Offset.Unspecified && doubleTabState.second) {
            isLiked = doubleTabState.second
        }
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        GlideImage(
            model = post.authorDetails.profileImageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .border(
                    BorderStroke(width = 1.dp, color = White), shape = CircleShape
                )
                .clip(shape = CircleShape)
                .clickable {
//                    onClickUser.invoke(item.authorDetails.userId)
                    onClickUser.invoke(123)
                },
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = null,
            modifier = Modifier
                .offset(y = (-10).dp)
                .size(20.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(5.5.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )

        12.dp.Space()


        LikeIconButton(isLiked = isLiked,
            likeCount = post.videoStats.likes.toString(),
            onLikedClicked = {
                isLiked = it
                onClickLike(isLiked)
                post.currentViewerInteraction.isLikedByYou = it
            })


        Icon(painter = painterResource(id = R.drawable.ic_comment),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(33.dp)
                .clickable {
                    onclickComment(post.id)
                })
        Text(
            text = post.videoStats.comments.toString(),
            style = MaterialTheme.typography.labelMedium,
            color = White
        )
        16.dp.Space()


        SaveButton(isSaved = isSaved,
            saveCount = post.videoStats.favourites.toString(),
            onSaveClick = {
                isSaved = it
                onClickFavourite(isSaved)
                post.currentViewerInteraction.isAddedToFavourite = it
            })

        Text(
            text = post.videoStats.favourites.toString(),
            style = MaterialTheme.typography.labelMedium,
            color = White
        )
        14.dp.Space()

        Icon(
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onClickShare?.let { onClickShare.invoke() } ?: run {
                        context.share(
                            text = "https://github.com/puskal-khadka"
                        )
                    }
                }
        )
        Text(
            text = post.videoStats.shares.toString(),
            style = MaterialTheme.typography.labelMedium,
            color = White
        )
        20.dp.Space()

        RotatingAudioView(post.authorDetails.profileImageUrl)

    }
}
