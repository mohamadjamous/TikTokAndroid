package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
    enabled: Boolean,
    totalTimeSeconds: Int = 60,
    modifier: Modifier = Modifier,
    onTimerFinish: (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    var remainingTime by remember { mutableIntStateOf(totalTimeSeconds) }

    var color by remember {
        mutableStateOf(Color.Gray)
    }
    var text by remember {
        mutableStateOf("Resend code ${remainingTime}s")
    }

    LaunchedEffect(enabled) {
        if (enabled) {
            color = Color.Blue
            text = "Resend code"
        } else {
            color = Color.Gray
            text = "Resend code ${remainingTime}s"
        }
    }

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

    TextButton(
        modifier = modifier,
        onClick = {
            onClick()
        },
        enabled = enabled
    ) {

        Text(
            text = text,
            fontSize = 15.sp,
            color = color
        )

    }
}