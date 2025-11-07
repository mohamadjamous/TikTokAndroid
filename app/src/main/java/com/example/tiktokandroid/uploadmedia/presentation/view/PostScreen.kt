package com.example.tiktokandroid.uploadmedia.presentation.view

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Drafts
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.auth.data.model.AuthUiState
import com.example.tiktokandroid.core.presentation.components.BackButton
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.core.presentation.components.CustomLoadingView
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.theme.PrimaryColor
import com.example.tiktokandroid.uploadmedia.data.model.PostUiState
import com.example.tiktokandroid.uploadmedia.presentation.components.PostOptionItem
import com.example.tiktokandroid.uploadmedia.presentation.components.VideoThumbnailWithPreview
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.PostViewModel

@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    videoUri: Uri? = null,
    viewModel: PostViewModel = hiltViewModel()
) {

    var description by remember { mutableStateOf("") }
    var allowComments by remember { mutableStateOf(true) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    val currentUser = viewModel.currentUser.value
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        when (state) {
            is PostUiState.Idle -> { /* Show initial screen */
            }

            is PostUiState.Loading -> {
                error = false
                loading = true
            }

            is PostUiState.Success -> {
                loading = false
                error = false

                onBackPressed()
                Toast.makeText(context, "Video uploaded successfully!", Toast.LENGTH_LONG).show()
                viewModel.resetUiState()
            }

            is PostUiState.Error -> {
                loading = false
                error = true
            }
        }
    }


    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                BackButton {
                    onBackPressed()
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Post",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1.3f))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        disabledContainerColor = Color(0xFFF5F5F5),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = "Create more informative content when you go into greater detail with 4000 characters.",
                            color = Color.Gray
                        )
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                videoUri?.let {
                    VideoThumbnailWithPreview(
                        it,
                        modifier = Modifier.width(110.dp).height(160.dp)
                    )
                }

            }


            Spacer(modifier = Modifier.weight(7f))

            PostOptionItem(
                text = "Allow comments",
                icon = Icons.Outlined.Comment,
                checked = allowComments,
                onCheckedChanged = {
                    allowComments = it
                }
            )


            Spacer(modifier = Modifier.weight(1f))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 55.dp)
            ) {

                CustomButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 5.dp),
                    text = "Drafts",
                    containerColor = Color.LightGray,
                    contentColor = Color.Black,
                    onClick = {

                    },
                    leadingIcon = Icons.Outlined.Upload
                )

                CustomButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 5.dp),
                    text = "Post",
                    containerColor = PrimaryColor,
                    contentColor = Color.White,
                    onClick = {
                        if (description.isEmpty()) {
                            Toast.makeText(
                                context,
                                "make sure to add description!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (description.length > 4000) {
                            Toast.makeText(context, "description is too long!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            videoUri?.let {
                                viewModel.uploadPost(
                                    post = Post(
                                        userId = currentUser?.id ?: "",
                                        allowComments = allowComments,
                                        username = currentUser?.username ?: "",
                                        description = description
                                    ),
                                    videoUri = it
                                )
                            }
                        }
                    },
                    leadingIcon = Icons.Outlined.Drafts
                )


            }
        }

        if (loading) {
            CustomLoadingView()
        }

    }


}




@Preview(showSystemUi = true)
@Composable
private fun PostScreenPreview() {
    PostScreen()
}