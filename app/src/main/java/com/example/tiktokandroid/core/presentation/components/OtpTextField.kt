package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpTextField(
    length: Int = 6,
    modifier: Modifier = Modifier,
    onOtpComplete: (String) -> Unit = {}
) {
    var otp by rememberSaveable { mutableStateOf("") }
    var completed by remember { mutableStateOf(false) } // track completion


    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 0 until length) {
            val char = otp.getOrNull(i)?.toString() ?: ""
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (char.isNotEmpty()) Color(0xFFE0E0E0) else Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (char.isNotEmpty()) Color.Gray else Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = char, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }

    // Hidden text field to capture input
    BasicTextField(
        value = otp,
        onValueChange = {
            if (it.length <= length && it.all { c -> c.isDigit() }) {
                otp = it
                if (it.length == length && !completed) {
                    completed = true
                    onOtpComplete(it)
                } else if (it.length < length) {
                    completed = false
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        cursorBrush = SolidColor(Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .height(0.dp)
    )
}