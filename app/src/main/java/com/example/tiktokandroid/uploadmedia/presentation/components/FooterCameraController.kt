package com.example.tiktokandroid.uploadmedia.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.R
import com.example.tiktokandroid.feed.presentation.components.MediumSpace
import com.example.tiktokandroid.theme.White
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.CameraCaptureOptions
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.Tabs
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalSnapperApi::class)
@Composable
fun FooterCameraController(
    modifier: Modifier = Modifier.fillMaxWidth(),
    cameraOpenType: Tabs,
    onClickEffect: () -> Unit,
    onClickOpenFile: () -> Unit,
    onclickCameraCapture: () -> Unit,
    isEnabledLayout: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    val backgroundShapeWidth = 62.dp
    val itemHorizontalPadding = 26.dp
    val screenHalfWidth = LocalConfiguration.current.screenWidthDp.div(2).dp
    val horizontalContentPadding = screenHalfWidth.minus(itemHorizontalPadding.div(2))
    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = when (cameraOpenType) {
            Tabs.CAMERA -> 2
            Tabs.STORY -> 1
            else -> 0
        }
    )

    val layoutInfo = rememberLazyListSnapperLayoutInfo(
        lazyListState = lazyListState,
        endContentPadding = screenHalfWidth.minus(30.dp),
        snapOffsetForItem = SnapOffsets.Center
    )
    val captureOptions = remember {
        when (cameraOpenType) {
            Tabs.CAMERA -> CameraCaptureOptions.values().toMutableList().apply {
                removeAll(listOf(CameraCaptureOptions.TEXT, CameraCaptureOptions.VIDEO))
            }
            Tabs.STORY -> CameraCaptureOptions.values().toMutableList().apply {
                removeAll(
                    listOf(CameraCaptureOptions.FIFTEEN_SECOND, CameraCaptureOptions.SIXTY_SECOND)
                )
            }
            else -> emptyList()
        }
    }

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .alpha(alphaForInteractiveView(isEnabledLayout))
                    .size(width = backgroundShapeWidth, height = 22.dp)
                    .background(color = White, shape = RoundedCornerShape(16.dp))
            )
            LazyRow(
                modifier = Modifier.alpha(alphaForInteractiveView(isEnabledLayout)),
                horizontalArrangement = Arrangement.spacedBy(26.dp),
                state = lazyListState,
                userScrollEnabled = isEnabledLayout,
                contentPadding = PaddingValues(horizontal = horizontalContentPadding),
                flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
            ) {
                itemsIndexed(captureOptions) { index, it ->
                    val isCurrentItem = layoutInfo.currentItem?.index == index
                    Text(
                        text = it.value,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.clickable {
                            if (isEnabledLayout) {
                                coroutineScope.launch {
                                    lazyListState.animateScrollToItem(index)
                                }
                            }
                        },
                        color = if (isCurrentItem) Color.Black else Color.White
                    )
                }
            }
        }

        MediumSpace()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.clickable {
                    if (isEnabledLayout) {
                        onClickEffect()
                    }
                }) {
                Image(
                    painter = painterResource(id = R.drawable.img_effect_placeholder),
                    contentDescription = null,
                    modifier = Modifier
                        .alpha(alphaForInteractiveView(isEnabledLayout))
                        .size(32.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = stringResource(id = R.string.effects),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.alpha(alphaForInteractiveView(isEnabledLayout))
                )
            }
            val captureButtonColor =
                when (captureOptions.getOrNull(layoutInfo.currentItem?.index ?: -1)) {
                    CameraCaptureOptions.FIFTEEN_SECOND, CameraCaptureOptions.SIXTY_SECOND, CameraCaptureOptions.VIDEO -> MaterialTheme.colorScheme.primary
                    else -> White
                }
            CaptureButton(
                modifier = Modifier.alpha(alphaForInteractiveView(isEnabledLayout)),
                color = captureButtonColor,
                onClickCapture = onclickCameraCapture
            )

            IconButton(
                onClick = { onClickOpenFile() },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(72.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_upload_placeholder),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = stringResource(id = R.string.upload),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

        }
        MediumSpace()
    }
}