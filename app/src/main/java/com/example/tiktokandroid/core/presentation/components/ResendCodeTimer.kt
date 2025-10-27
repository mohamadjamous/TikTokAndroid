package com.example.tiktokandroid.core.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun ResendCodeTimer(
    start: Boolean,
    totalTimeSeconds: Int = 60,
    modifier: Modifier = Modifier,
    onTimerFinish: (() -> Unit)? = null
) {
    var remainingTime by remember { mutableStateOf(totalTimeSeconds) }

    LaunchedEffect(start) {
        if (start) {
            remainingTime = totalTimeSeconds
            while (remainingTime > 0) {
                delay(1000L)
                remainingTime -= 1
            }
            onTimerFinish?.invoke()
        }
    }

    Text(
        modifier = modifier,
        text = "Resend code ${remainingTime}s",
        fontSize = 15.sp,
        color = Color.Gray
    )
}