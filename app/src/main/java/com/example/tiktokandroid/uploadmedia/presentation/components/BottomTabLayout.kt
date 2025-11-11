package com.example.tiktokandroid.uploadmedia.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.theme.White
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.DisableRippleInteractionSource
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.Tabs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomTabLayout(
    pagerState: PagerState,
    onClickTab: (position: Int) -> Unit
) {
    val edgePadding = LocalConfiguration.current.screenWidthDp.div(2).dp

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        divider = {},
        indicator = {},
        edgePadding = edgePadding
    ) {
        Tabs.values().asList().forEachIndexed { index, tab ->
            val isSelected = pagerState.currentPage == index
            Tab(selected = isSelected, onClick = {
                onClickTab(index)
            }, interactionSource = remember { DisableRippleInteractionSource() }, text = {
                val textColor = if (isSelected) White
                else White.copy(alpha = 0.6f)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = tab.rawValue),
                        style = MaterialTheme.typography.labelLarge,
                        color = textColor
                    )
                    Box(
                        modifier = Modifier
                            .alpha(if (isSelected) 1f else 0f)
                            .padding(top = 10.dp)
                            .size(5.dp)
                            .background(color = White, shape = CircleShape)
                    )
                }
            })
        }
    }
}
