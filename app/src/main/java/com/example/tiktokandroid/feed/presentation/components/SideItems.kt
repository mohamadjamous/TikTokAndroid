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
import androidx.compose.runtime.collectAsState
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
import androidx.media3.common.util.UnstableApi
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tiktokandroid.R
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.utils.IntentUtils.share
import com.example.tiktokandroid.feed.presentation.viewmodel.FeedViewModel
import com.example.tiktokandroid.theme.White

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SideItems(
    modifier: Modifier,
    post: Post,
    doubleTabState: Triple<Offset, Boolean, Float>,
    onclickComment: (videoId: String, onNewComment: () -> Unit) -> Unit,
    onClickUser: (userId: Long) -> Unit,
    onClickShare: (() -> Unit)? = null,
    onClickLike: (Boolean) -> Unit,
    onClickFavourite: (isFav: Boolean) -> Unit,
    viewModel : FeedViewModel
) {

    LaunchedEffect(post.id) {
        viewModel.fetchVideoStats(post.id)
    }

    val context = LocalContext.current
    val likedMap by viewModel.likedMap.collectAsState()
    val savedMap by viewModel.savedMap.collectAsState()

    val isLikedFromVm = likedMap[post.id] ?: post.currentViewerInteraction.isLikedByYou
    val isSavedFromVm = savedMap[post.id] ?: post.currentViewerInteraction.isAddedToFavourite

    // Local UI states (needed for animation)
    var isLiked by remember(post.id) { mutableStateOf(isLikedFromVm) }
    var isSaved by remember(post.id) { mutableStateOf(isSavedFromVm) }

    // Counters as mutable states for instant updates
    var likeCount by remember(post.id) { mutableStateOf(post.videoStats.likes) }
    var favouriteCount by remember(post.id) { mutableStateOf(post.videoStats.favourites) }
    var commentCount by remember(post.id) { mutableStateOf(post.videoStats.comments) }
    var shareCount by remember(post.id) { mutableStateOf(post.videoStats.shares) }


    // Sync VM → UI whenever backend changes
    LaunchedEffect(post.id, isLikedFromVm) {
        isLiked = isLikedFromVm
    }
    LaunchedEffect(post.id, isSavedFromVm) {
        isSaved = isSavedFromVm
    }


    // Handle double-tap → only affects local UI
    LaunchedEffect(doubleTabState) {
        if (doubleTabState.first != Offset.Unspecified && doubleTabState.second) {
            isLiked = true
            likeCount += 1
            onClickLike(isLiked)
        }
    }



    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        GlideImage(
            model = post.author.profileImageUrl,
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
            likeCount = likeCount.toString(),
            onLikedClicked = {
                isLiked = it
                likeCount += if (it) 1 else -1
                onClickLike(it)
                post.currentViewerInteraction.isLikedByYou = it
            })


        Icon(painter = painterResource(id = R.drawable.ic_comment),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(33.dp)
                .clickable {
                    onclickComment(post.id) {
                        commentCount += 1
                    }
                })
        Text(
            text = commentCount.toString(),
            style = MaterialTheme.typography.labelMedium,
            color = White
        )
        16.dp.Space()


        SaveButton(isSaved = isSaved,
            saveCount = favouriteCount.toString(),
            onSaveClick = {
                isSaved = it
                favouriteCount += if (it) 1 else -1
                onClickFavourite(it)
                post.currentViewerInteraction.isAddedToFavourite = it
            })


        Icon(
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onClickShare?.let { onClickShare.invoke() } ?: run {
                        context.share(
                            text = ""
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

        RotatingAudioView(post.author.profileImageUrl)

    }
}
