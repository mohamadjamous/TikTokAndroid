package com.example.tiktokandroid.feed.presentation.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.R
import com.example.tiktokandroid.theme.White

@Composable
fun LikeIconButton(
    isLiked: Boolean, likeCount: String, onLikedClicked: (Boolean) -> Unit
) {

    val maxSize = 38.dp

    // This remembers the previous click, NOT backend fetch
    var animateTrigger by remember { mutableStateOf(false) }

    val iconSize by animateDpAsState(targetValue = if (animateTrigger) maxSize else 32.dp,
        animationSpec = keyframes {
            durationMillis = 400
            24.dp.at(50)
            maxSize.at(190)
            26.dp.at(330)
            32.dp.at(400).with(FastOutLinearInEasing)
        })

    Box(
        modifier = Modifier
            .size(maxSize)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) {
                // Only trigger animation when user clicks
                animateTrigger = true
                onLikedClicked(!isLiked)
            }, contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = null,
            tint = if (isLiked) MaterialTheme.colorScheme.primary else Color.White,
            modifier = Modifier.size(iconSize)
        )
    }

    Text(text = likeCount, style = MaterialTheme.typography.labelMedium,
        color = White)
    16.dp.Space()

    // Reset animation trigger when state changes back to unliked
    LaunchedEffect(isLiked) {
        if (!isLiked) animateTrigger = false
    }
}