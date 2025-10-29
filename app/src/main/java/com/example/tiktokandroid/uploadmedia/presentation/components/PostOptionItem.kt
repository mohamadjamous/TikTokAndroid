package com.example.tiktokandroid.uploadmedia.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PostOptionItem(
    modifier: Modifier = Modifier,
    text: String = "Option",
    icon: ImageVector = Icons.Filled.Image,
    onCheckedChanged: (Boolean) -> Unit = {},
    checked: Boolean = false
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = icon,
            contentDescription = "",
            tint = Color.Gray
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = text,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = checked,
            onCheckedChange = {
                onCheckedChanged(it)
            }
        )

        Spacer(modifier = Modifier.width(20.dp))
    }

}

@Preview
@Composable
private fun PostOptionItemPreview() {
    PostOptionItem()
}