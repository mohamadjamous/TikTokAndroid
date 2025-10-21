package com.example.tiktokandroid.core.presentation.components

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    leadingIcon: ImageVector,
    contentColor: Color = Color.Black,
    containerColor: Color = Color.White
) {


    

}

@Preview
@Composable
private fun LoginButtonPreview() {

    LoginButton(
        text = "User email / username",
        onClick = {},
        leadingIcon = Icons.Default.Person
    )
}