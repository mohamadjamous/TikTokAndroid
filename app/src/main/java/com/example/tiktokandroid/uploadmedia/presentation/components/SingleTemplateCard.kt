package com.example.tiktokandroid.uploadmedia.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.TemplateModel
import kotlin.math.absoluteValue


@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun SingleTemplateCard(
    page: Int,
    pagerState: PagerState,
    item: TemplateModel,
) {
    val pageOffset =
        ((pagerState.currentPage - page) + (pagerState.currentPageOffsetFraction)).absoluteValue
    Card(
        modifier = Modifier
            .graphicsLayer {
                lerp(
                    start = 0.82f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            Modifier
                .blur(if (pagerState.settledPage == page) 0.dp else 60.dp)
        )
        {
            println("ParseMediaLink: ${item.parseMediaLink()}")
            GlideImage(
                model = item.parseMediaLink(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

        }
    }
}
