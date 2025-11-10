package com.example.tiktokandroid.feed.presentation.view.screens

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tiktokandroid.R
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.feed.presentation.components.Space
import com.example.tiktokandroid.feed.presentation.components.VideoPlayer
import com.example.tiktokandroid.theme.DarkBlue
import com.example.tiktokandroid.theme.SubTextColor
import com.example.tiktokandroid.theme.White
import com.example.tiktokandroid.theme.WhiteAlpha95
import kotlin.math.absoluteValue

/**
 * Created by Puskal Khadka on 3/14/2023.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FollowingScreen(
    parentPagerState: PagerState,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        80.dp.Space()
        Text(
            text = stringResource(id = R.string.trending_creators),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        10.dp.Space()
        Text(
            text = stringResource(id = R.string.follow_and_account_to_see),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            textAlign = TextAlign.Center,
            color = SubTextColor
        )
        22.dp.Space()

        if (parentPagerState.settledPage == 0) {
//            viewState?.contentCreators?.let {
//                VideoItem(
//                    creatorList = it,
//                    onclickUser = { userId ->
//                        //navController.navigate("$CREATOR_PROFILE_ROUTE/$userId") }
//                        )
//                    }
//            }
        }

    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoItem(
    creatorList: List<ContentCreatorFollowingModel>,
    onclickUser: (userId: Long) -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = {12}
    )
    HorizontalPager(
        contentPadding = PaddingValues(horizontal = 54.dp),
        state = pagerState,
        beyondViewportPageCount = 1
    ) {
        CreatorCard(
            page = it,
            pagerState = pagerState,
            item = creatorList[it],
            onClickUser = onclickUser,
            onClickFollow = {}
        )
    }
}

data class ContentCreatorFollowingModel(
    val userModel: User,
    val coverVideo: Post
) {
    fun parseVideo(): Uri = Uri.parse("asset:///videos/${coverVideo.videoUrl}")
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreatorCard(
    page: Int,
    pagerState: PagerState,
    item: ContentCreatorFollowingModel,
    onClickFollow: (userId: Long) -> Unit,
    onClickUser: (userId: Long) -> Unit
) {
    val pageOffset =
        ((pagerState.currentPage - page) + (pagerState.currentPageOffsetFraction)).absoluteValue
    Card(
        modifier = Modifier
            .graphicsLayer {
                lerp(
                    start = 0.9f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            Modifier
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        val color: Color = lerp(
                            Color.Black.copy(alpha = 0.59f),
                            Color.Transparent,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        drawRect(color)
                    }
                }
                .height(340.dp)
        )

        {
            VideoPlayer(
                video = item.coverVideo,
                pagerState = pagerState,
                pageIndex = page,
                onSingleTap = {
                    onClickUser(123)
                },
                onDoubleTap = { exoPlayer: ExoPlayer, offset: Offset -> },
                onVideoDispose = {},
                onVideoGoBackground = {})

            Icon(
                painterResource(id = R.drawable.ic_cancel),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                GlideImage(
                    model = item.userModel.profileImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .size(70.dp)
                        .border(
                            BorderStroke(width = 1.dp, color = White), shape = CircleShape
                        )
                        .clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = item.userModel.fullName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Text(
                    text = "@${item.userModel.username}",
                    style = MaterialTheme.typography.labelMedium,
                    color = WhiteAlpha95
                )
                Button(
                    onClick = {
                        onClickFollow(123)
                    }, modifier = Modifier
                        .padding(top = 2.dp)
                        .padding(horizontal = 36.dp)
                        .fillMaxWidth(), shape = RoundedCornerShape(2.dp)
                ) {
                    Text(text = stringResource(id = R.string.follow))
                }
                12.dp.Space()
            }
            20.dp.Space()
        }
    }
}