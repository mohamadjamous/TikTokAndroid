package com.example.tiktokandroid.core.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CustomLoadingView(
    modifier: Modifier = Modifier,
    size: Dp = 65.dp,
    ballSize: Dp = 13.dp,
    primaryColor: Color = Color.Red,
    secondaryColor: Color = Color.White,
    duration: Int = 800
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = modifier
                .size(size + 32.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        )
        {
            Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radiusX =
                        (size.toPx() / 2 - ballSize.toPx() / 2) * 0.3f  // closer horizontally
                    val radiusY = radiusX / 2  // vertical still smaller for 3D


                    // Convert rotation to radians
                    val angle1 = Math.toRadians(rotation.toDouble())
                    val angle2 = Math.toRadians((rotation + 180) % 360.0)

                    // Ball 1
                    val x1 = center.x + radiusX * cos(angle1)
                    val y1 = center.y + radiusY * sin(angle1)
                    val scale1 = 0.5f + 0.5f * (1 + sin(angle1))  // scale for depth

                    // Ball 2
                    val x2 = center.x + radiusX * cos(angle2)
                    val y2 = center.y + radiusY * sin(angle2)
                    val scale2 = 0.5f + 0.5f * (1 + sin(angle2))

                    // Draw behind first
                    if (scale1 < scale2) {
                        drawCircle(
                            color = primaryColor,
                            radius = ((ballSize.toPx() / 2) * scale1).toFloat(),
                            center = Offset(x1.toFloat(), y1.toFloat())
                        )
                        drawCircle(
                            color = secondaryColor,
                            radius = ((ballSize.toPx() / 2) * scale2).toFloat(),
                            center = Offset(x2.toFloat(), y2.toFloat())
                        )
                    } else {
                        drawCircle(
                            color = secondaryColor,
                            radius = ((ballSize.toPx() / 2) * scale2).toFloat(),
                            center = Offset(x2.toFloat(), y2.toFloat())
                        )
                        drawCircle(
                            color = primaryColor,
                            radius = ((ballSize.toPx() / 2) * scale1).toFloat(),
                            center = Offset(x1.toFloat(), y1.toFloat())
                        )
                    }
                }
            }
        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun FullScreenLoadingPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        CustomLoadingView()
    }
}
