package com.example.tiktokandroid.feed.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tiktokandroid.R
import com.example.tiktokandroid.feed.data.model.FeedUiState
import com.example.tiktokandroid.feed.data.model.HighlightedEmoji
import com.example.tiktokandroid.feed.presentation.viewmodel.CommentListViewModel
import com.example.tiktokandroid.theme.GrayMainColor
import com.example.tiktokandroid.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun CommentUserField(
    onClick: (String) -> Unit = {},
    viewModel: CommentListViewModel
) {
    var input by remember { mutableStateOf("") }
    val context = LocalContext.current
    val commentUiState by viewModel.commentUiState.collectAsState()

    val loading = commentUiState is FeedUiState.Loading

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

            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                },
                shape = RoundedCornerShape(36.dp),
                placeholder = {
                    Text(text = stringResource(R.string.add_comment))
                },
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    backgroundColor = GrayMainColor,
//                    unfocusedBorderColor = Color.Transparent
//                ),
                modifier = Modifier.height(56.dp),
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


            // Send button
            Box {
                Button(
                    onClick = {
                        if (input.isNotBlank() && !loading) {
                            println("InputComment: $input")
                            onClick(input)
                            input = ""
                        } else {
                            Toast.makeText(context, "Comment cannot be empty!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryColor,
                        contentColor = Color.Unspecified
                    ),
                    contentPadding = PaddingValues(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    enabled = !loading
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.ArrowCircleUp,
                            contentDescription = "Send",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }


    }
}

@Preview
@Composable
fun SendButton(
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
            contentColor = Color.Unspecified
        ),
        contentPadding = PaddingValues(12.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowCircleUp,
            contentDescription = "Send",
            modifier = Modifier.size(24.dp)
        )
    }
}
