package com.example.tiktokandroid.feed.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.R
import com.example.tiktokandroid.core.presentation.components.LoadingEffect
import com.example.tiktokandroid.feed.data.model.CommentList
import com.example.tiktokandroid.feed.data.model.FeedUiState
import com.example.tiktokandroid.feed.presentation.viewmodel.CommentListViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Puskal Khadka on 3/22/2023.
 */
@Composable
fun CommentListScreen(
    viewModel: CommentListViewModel = hiltViewModel(),
    onClickCancel: () -> Unit,
    videoId : String
) {
    val uiState by viewModel.uiState.collectAsState()

    val comments = viewModel.commentsList.collectAsState().value

    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        .format(Date())

    val context = LocalContext.current


    val currentUser = viewModel.currentUser.value


    // Runs once when the composable first appears
    LaunchedEffect(Unit) {
        viewModel.getCommentsList(videoId)
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is FeedUiState.Error -> {
                errorMessage = (uiState as FeedUiState.Error).message
                loading = false
            }

            FeedUiState.Idle -> {}
            FeedUiState.Loading -> {
                loading = true
            }

            is FeedUiState.Success -> {
                loading = false
                errorMessage = ""
            }
        }
    }



    Box(
        modifier = Modifier
            .fillMaxHeight(0.75f)
            .fillMaxWidth()
    ) {

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            12.dp.Space()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "${comments.size} ${stringResource(id = R.string.comments)}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.align(Alignment.Center)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable { onClickCancel() }
                )
            }

            6.dp.Space()
            LazyColumn(
                contentPadding = PaddingValues(top = 4.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(comments) { comment ->
                    CommentItem(comment)
                }
            }

            CommentUserField(
                onClick = { comment ->
                    if (currentUser != null){
                        viewModel.uploadComment(
                            CommentList.Comment(
                                commentBy = currentUser,
                                comment = comment,
                                createdAt = currentDateTime,
                                totalLike = 0,
                                totalDisLike = 0,
                                threadCount = 0,
                                thread = emptyList(),
                                videoId = videoId
                            )
                        )
                    }else{
                        Toast.makeText(context, "Login first in order to comment", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                viewModel = viewModel
            )
        }

        // Loading indicator overlay
        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                LoadingEffect()
            }
        }

        // Error message overlay
        if (errorMessage.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = errorMessage,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(24.dp)
                        .background(Color.Red.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }
    }
}
