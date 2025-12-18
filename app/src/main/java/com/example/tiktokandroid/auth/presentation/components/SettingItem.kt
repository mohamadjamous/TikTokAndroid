package com.example.tiktokandroid.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.theme.Black
import com.example.tiktokandroid.theme.Gray
import com.example.tiktokandroid.theme.White
import com.example.tiktokandroid.uploadmedia.presentation.components.CustomButton

@Preview(showSystemUi = true)
@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    text: String = "text",
    buttonText: String = "text",
    icon: ImageVector = Icons.Default.Person,
    buttonVisible: Boolean = true,
    onButtonClick: () -> Unit = {},
    onItemClick: () -> Unit = {},
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick()
            }
            .clip(shape = RoundedCornerShape(8.dp))
            .background(White)
            .padding(10.dp)
            ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = icon,
                contentDescription = null,
                tint = Gray
            )


            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text,
                color = Black,
                style = MaterialTheme.typography.titleSmall
            )
        }


        if (buttonVisible) {
            CustomButton(
                buttonText = buttonText,
                modifier = Modifier.height(35.dp)
            ) { onButtonClick }

        } else {
            Icon(
                modifier = Modifier.size(15.dp),
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = Gray
            )
        }

    }

}