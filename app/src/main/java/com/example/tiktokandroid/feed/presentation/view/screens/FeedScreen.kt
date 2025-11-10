package com.example.tiktokandroid.feed.presentation.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.example.tiktokandroid.R
import com.example.tiktokandroid.theme.White
import kotlinx.coroutines.launch


@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(UnstableApi::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {


    val tabItems = arrayListOf(R.string.following, R.string.for_you)
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        HorizontalPager(
            state = pagerState,
        ) {
            when (it) {
                0 -> FollowingScreen(pagerState)
                1 -> ForYouTabScreen(navController = navController)
            }
        }

        val edge = LocalConfiguration.current.screenWidthDp.dp.div(2).minus(100.dp)

        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            divider = {},
            modifier = Modifier.padding(top = 8.dp),
            indicator = { tabPositions ->
                val modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .padding(horizontal = 38.dp)

                TabRowDefaults.Indicator(
                    modifier, color = White
                )
            },
            edgePadding = edge
        ) {

            tabItems.forEachIndexed { index, item ->
                val isSelected = pagerState.currentPage == index
                Tab(
                    selected = isSelected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        val textStyle =
                            if (isSelected) MaterialTheme.typography.titleMedium.merge(
                                TextStyle(color = Color.White)
                            )
                            else TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 17.sp,
                                color = White.copy(alpha = 0.6f)
                            )
                        Text(text = stringResource(id = item), style = textStyle)
                    }
                )
            }
        }
    }

}


