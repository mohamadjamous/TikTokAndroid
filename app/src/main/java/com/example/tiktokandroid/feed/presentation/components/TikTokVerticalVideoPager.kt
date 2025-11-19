package com.example.tiktokandroid.feed.presentation.components


import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.theme.Gray20
import com.example.tiktokandroid.theme.GrayLight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.tiktokandroid.R
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.core.utils.IntentUtils.share
import com.example.tiktokandroid.feed.presentation.viewmodel.FeedViewModel
import com.example.tiktokandroid.theme.White

/**
 * Created by Puskal Khadka on 3/16/2023.
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun TikTokVerticalVideoPager(
    modifier: Modifier = Modifier,
    videos: List<Post>,
    initialPage: Int? = 0,
    showUploadDate: Boolean = false,
    onclickComment: (videoId: String, onNewComment: () -> Unit) -> Unit,
    onClickLike: (videoId: String, likeStatus: Boolean) -> Unit,
    onClickFavourite1: (videoId: String, likeStatus: Boolean) -> Unit,
    onClickAudio: (Post) -> Unit,
    onClickUser: (userId: Long) -> Unit,
    onClickFavourite: (isFav: Boolean) -> Unit = {},
    onClickShare: (() -> Unit)? = null,
    onPageChanged: (Int) -> Unit,
    currentUser: User?,
    viewModel : FeedViewModel
) {
    val pagerState = rememberPagerState(initialPage = initialPage ?: 0, pageCount = { videos.size })
    val coroutineScope = rememberCoroutineScope()
    val localDensity = LocalDensity.current

    val fling = PagerDefaults.flingBehavior(
        state = pagerState
    )

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    VerticalPager(
        state = pagerState,
        flingBehavior = fling,
        beyondViewportPageCount = 1,
        modifier = modifier
    ) {
        var pauseButtonVisibility by remember { mutableStateOf(false) }
        var doubleTapState by remember {
            mutableStateOf(
                Triple(
                    Offset.Unspecified,
                    false,
                    0f
                )
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            VideoPlayer(
                videos[it], pagerState, it, onSingleTap = {
                    pauseButtonVisibility = it.isPlaying
                    it.playWhenReady = !it.isPlaying
                },
                onDoubleTap = { exoPlayer, offset ->
                    coroutineScope.launch {
                        videos[it].currentViewerInteraction.isLikedByYou = true
                        onClickLike(videos[it].id, true)
                        val rotationAngle = (-10..10).random()
                        doubleTapState = Triple(offset, true, rotationAngle.toFloat())
                        delay(400)
                        doubleTapState = Triple(offset, false, rotationAngle.toFloat())
                    }
                },
                onVideoDispose = { pauseButtonVisibility = false },
                onVideoGoBackground = { pauseButtonVisibility = false }

            )


            Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    FooterUi(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        item = videos[it],
                        showUploadDate = showUploadDate,
                        onClickAudio = onClickAudio,
                        onClickUser = onClickUser,
                    )

                    SideItems(
                        modifier = Modifier,
                        videos[it],
                        doubleTabState = doubleTapState,
                        onclickComment = onclickComment,
                        onClickUser = onClickUser,
                        onClickFavourite = { saved ->
                            onClickFavourite1(videos[it].id, saved)
                        },
                        onClickShare = onClickShare,
                        onClickLike = { liked ->
                            onClickLike(videos[it].id, liked)
                        },
                        viewModel = viewModel
                    )
                }
                12.dp.Space()
            }


            AnimatedVisibility(
                visible = pauseButtonVisibility,
                enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.5f),
                exit = scaleOut(tween(150)),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(36.dp)
                )
            }

            val iconSize = 110.dp
            AnimatedVisibility(
                visible = doubleTapState.second,
                enter = scaleIn(spring(Spring.DampingRatioMediumBouncy), initialScale = 1.3f),
                exit = scaleOut(
                    tween(600), targetScale = 1.58f
                ) + fadeOut(tween(600)) + slideOutVertically(
                    tween(600)
                ),
                modifier = Modifier.run {
                    if (doubleTapState.first != Offset.Unspecified) {
                        this.offset(x = localDensity.run {
                            doubleTapState.first.x.toInt().toDp().plus(-iconSize.div(2))
                        }, y = localDensity.run {
                            doubleTapState.first.y.toInt().toDp().plus(-iconSize.div(2))
                        })
                    } else this
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_like),
                    contentDescription = null,
                    tint = if (doubleTapState.second) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.8f
                    ),
                    modifier = Modifier
                        .size(iconSize)
                        .rotate(doubleTapState.third)
                )
            }


        }
    }

}

@Composable
fun Dp.Space() = Spacer(modifier = Modifier.height(this))

@Composable
fun SmallSpace() = Spacer(modifier = Modifier.height(16.dp))

@Composable
fun MediumSpace() = Spacer(modifier = Modifier.height(26.dp))

@Composable
fun LargeSpace() = Spacer(modifier = Modifier.height(32.dp))
