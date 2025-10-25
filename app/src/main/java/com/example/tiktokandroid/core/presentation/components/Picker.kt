package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Picker(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    visibleItemsCount: Int = 5,
    itemHeight: Dp = 40.dp
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val centerIndex = remember { derivedStateOf { listState.firstVisibleItemIndex + visibleItemsCount / 2 } }

    val padding = visibleItemsCount / 2
    val paddedItems = List(padding) { "" } + items + List(padding) { "" }

    LaunchedEffect(centerIndex.value) {
        val actualIndex = centerIndex.value - padding
        if (actualIndex in items.indices) {
            onItemSelected(items[actualIndex])
        }
    }

    Box(
        modifier = Modifier
            .height(itemHeight * visibleItemsCount)
            .width(100.dp),
        contentAlignment = Alignment.Center
    ) {
        // Fading effect
        Box(
            Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.9f),
                            Color.Transparent,
                            Color.Transparent,
                            Color.White.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        // Highlight area (center item)
        Box(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(itemHeight)
        )

        LazyColumn(
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(listState),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.height(itemHeight * visibleItemsCount)
        ) {
            items(paddedItems.size) { index ->
                val isSelected = index == centerIndex.value
                Text(
                    text = paddedItems[index],
                    color = if (isSelected) Color.Black else Color.Gray,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = if (isSelected) 20.sp else 16.sp,
                    modifier = Modifier
                        .height(itemHeight)
                        .wrapContentHeight()
                )
            }
        }
    }
}