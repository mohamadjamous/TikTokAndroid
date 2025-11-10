package com.example.tiktokandroid.feed.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tiktokandroid.R
import com.example.tiktokandroid.feed.data.model.HighlightedEmoji
import com.example.tiktokandroid.theme.GrayMainColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun CommentUserField() {
    Column(
        modifier = Modifier
            .shadow(elevation = (0.4).dp)
            .padding(horizontal = 16.dp)
    )
    {
        HighlightedEmoji.values().toList().let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                it.forEach { emoji ->
                    Text(text = emoji.unicode, fontSize = 25.sp)
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = "", contentDescription = null, modifier = Modifier
                    .size(38.dp)
                    .background(
                        shape =
                            CircleShape, color = GrayMainColor
                    )
            )

            OutlinedTextField(value = "",
                onValueChange = {},
                shape = RoundedCornerShape(36.dp),
                placeholder = {
                    Text(text = stringResource(R.string.add_comment))
                },
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    containerColor = GrayMainColor,
//                    unfocusedBorderColor = Color.Transparent
//                ),
                modifier = Modifier.height(46.dp),
                enabled=false,
                trailingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.padding(end = 10.dp, start = 2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_mention),
                            contentDescription = null
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_emoji),
                            contentDescription = null
                        )
                    }

                }

            )
        }
    }
}