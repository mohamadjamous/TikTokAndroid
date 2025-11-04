package com.example.tiktokandroid.profile.presentation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.core.presentation.model.Post

/**
 * Created by Puskal Khadka on 3/25/2023.
 */


@Composable
fun VideoGrid(
    scrollState: ScrollState,
    videoList: List<Post>,
    onClickVideo: (video: Post, index: Int) -> Unit
) {
    val context = LocalContext.current
    LazyVerticalGrid(columns = GridCells.Fixed(3),
        modifier = Modifier
            .height(1000.dp)
            .nestedScroll(
                remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            return if (available.y > 0) Offset.Zero else
                                Offset(
                                    x = 0f,
                                    y = -scrollState.dispatchRawDelta(-available.y)
                                )
                        }
                    }
                }
            ),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        content = {
            itemsIndexed(videoList) { index, item ->
                PostRowItem(post = item, index = index, onClickVideo = onClickVideo)
            }
        })


}