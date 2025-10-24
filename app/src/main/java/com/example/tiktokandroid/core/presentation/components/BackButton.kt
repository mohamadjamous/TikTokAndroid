package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {}
) {
    IconButton(
        modifier = modifier,
        onClick = {
          onBackPressed()
        }
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "back"
        )
    }
}

@Preview
@Composable
private fun BackButtonPreview() {
    BackButton()
}