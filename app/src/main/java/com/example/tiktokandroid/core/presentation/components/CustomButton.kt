package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    containerColor: Color = Color(0xFFE5E5EA),
    contentColor: Color = Color.Black
) {


    Button(
        modifier = modifier.height(35.dp),
        shape = RoundedCornerShape(5.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }

}

@Preview
@Composable
private fun GrayCustomButtonPreview() {
    CustomButton(text = "edit", onClick = {})
}