package com.example.tiktokandroid.profile.presentation.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.auth.presentation.view.LoginScreen
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokRed
import com.example.tiktokandroid.profile.presentation.components.PostRowItem
import com.example.tiktokandroid.profile.presentation.viewmodel.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val videos = viewModel.videos.collectAsState().value

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val partialHeight = screenHeight * 0.75f

    Box(modifier = modifier.fillMaxSize()) {

        Column {

            // top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.weight(1.3f))

                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.weight(1f))

                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Menu, contentDescription = "More options")
                }

            }

            Divider(
                modifier = Modifier.padding(top = 5.dp)
            )

            if (false) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = modifier
                        .fillMaxSize()
                        .padding(bottom = 55.dp),
                    contentPadding = PaddingValues(bottom = 50.dp)
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ProfileHeaderView(
                                modifier = Modifier.padding(top = 30.dp),
                                imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/396e9/MainBefore.jpg",
                                username = "@lwis.hamilton"
                            )
                        }
                    }
                    items(videos.size) { index ->
                        PostRowItem(
                            modifier = Modifier.padding(2.dp),
                            post = videos[index]
                        )
                    }
                }

            } else {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(partialHeight),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        modifier = Modifier.size(150.dp),
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "More options"
                    )

                    Text(
                        text = "Login into existing account",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    CustomButton(
                        modifier = Modifier
                            .width(200.dp)
                            .height(65.dp)
                            .padding(top = 20.dp),
                        text = "Login",
                        containerColor = TikTokRed,
                        contentColor = Color.White,
                        onClick = {
                            showBottomSheet = true
                        }
                    )
                }
            }

        }


        if (showBottomSheet) {

            // Bottom sheet
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = Color.White
                )
            {
                // Sheet content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    // Top bar
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End)
                    {

                        IconButton(
                            onClick = {
                                showBottomSheet = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "close"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Content
                    LoginScreen()
                }
            }

        }

    }

}


@Composable
@Preview(showSystemUi = true)
fun ProfileViewPreview() {
    ProfileScreen()
}