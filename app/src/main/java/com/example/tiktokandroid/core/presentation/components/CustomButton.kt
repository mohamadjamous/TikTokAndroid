package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokLightGray

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    containerColor: Color = TikTokLightGray,
    contentColor: Color = Color.Black,
    loading: Boolean = false,
    height: Int = 50
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp),
        contentAlignment = Alignment.Center
    ) {


        Button(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(8.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
        ) {

            if (loading) {
                CircularProgressIndicator(
                    color = Color.White
                )
            } else {

                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            }
        }
    }

}

@Preview
@Composable
private fun GrayCustomButtonPreview() {
    CustomButton(text = "edit", onClick = {})
}