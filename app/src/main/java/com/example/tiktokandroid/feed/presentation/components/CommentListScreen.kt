package com.example.tiktokandroid.feed.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.R

/**
 * Created by Puskal Khadka on 3/22/2023.
 */


@Composable
fun CommentListScreen(
//    viewModel: CommentListViewModel = hiltViewModel(),
    onClickCancel: () -> Unit
) {
//    val viewState by viewModel.viewState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight(0.75f)
    ) {
        12.dp.Space()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
//                text = "${viewState?.comments?.totalComment ?: ""} ${stringResource(id = R.string.comments)}",
                text = "${123 ?: ""} ${stringResource(id = R.string.comments)}",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.align(Alignment.Center)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable {
                        onClickCancel()
                    }
            )
        }

        6.dp.Space()
        LazyColumn(contentPadding = PaddingValues(top = 4.dp), modifier = Modifier.weight(1f)) {
//            viewState?.comments?.comments?.let {
//                items(it) {
//                    CommentItem(it)
//                }
//            }
        }

        CommentUserField()
    }
}