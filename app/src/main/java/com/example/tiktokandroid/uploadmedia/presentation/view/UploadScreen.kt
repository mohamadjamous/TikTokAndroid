package com.example.tiktokandroid.uploadmedia.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokRed
import com.example.tiktokandroid.uploadmedia.presentation.components.VideoListItem
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.UploadViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    modifier: Modifier = Modifier,
    navigateToPostScreen: (Uri) -> Unit = {},
    navigateToProfileScreen: () -> Unit = {},
    viewModel: UploadViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var videoList by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val currentUser by viewModel.currentUser
    var loggedIn by remember { mutableStateOf(false) }

    LaunchedEffect(currentUser) {
        loggedIn = currentUser != null
    }

    // Load videos when sheet opens
    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            videoList = viewModel.loadDeviceVideos(context)
        }
    }
    val videoPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_VIDEO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                videoPermission
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasPermission = granted }
    )


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Upload",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            CustomButton(
                modifier = Modifier
                    .width(200.dp)
                    .height(65.dp)
                    .padding(top = 20.dp),
                text = "Select Video",
                containerColor = TikTokRed,
                contentColor = Color.White,
                onClick = {

                    if (loggedIn) {

                        if (hasPermission) {
                            showBottomSheet = true
                            videoList = viewModel.loadDeviceVideos(context)
                        } else {
                            // Ask permission on click
                            permissionLauncher.launch(videoPermission)
                            Toast.makeText(
                                context,
                                "Allow storage permissions to upload media",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        navigateToProfileScreen()
                    }
                }
            )
        }

        // Bottom sheet for showing videos
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = bottomSheetState,
                dragHandle = { BottomSheetDefaults.DragHandle() }
            ) {
                Text(
                    "Select a video",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    items(videoList) { videoUri ->
                        VideoListItem(videoUri) {
                            // When user selects a video
                            showBottomSheet = false
                            navigateToPostScreen(videoUri)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UploadScreenPreview() {
    UploadScreen()
}