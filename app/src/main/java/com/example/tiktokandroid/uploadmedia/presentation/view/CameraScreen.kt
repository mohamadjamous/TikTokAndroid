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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tiktokandroid.core.utils.openAppSetting
import com.example.tiktokandroid.uploadmedia.presentation.components.CameraMicrophoneAccessPage
import com.example.tiktokandroid.uploadmedia.presentation.components.CameraPreview
import com.example.tiktokandroid.uploadmedia.presentation.components.VideoListItem
import com.example.tiktokandroid.uploadmedia.presentation.components.pickVisualMediaRequest
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.CameraMediaViewModel
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.PermissionType
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.Tabs
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.UploadViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

/**
 * Created by Puskal Khadka on 4/2/2023.
 */
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    navController: NavController,
    viewModel: CameraMediaViewModel,
    cameraOpenType: Tabs = Tabs.CAMERA,
    uploadViewModel: UploadViewModel = hiltViewModel(),
    navigateToPostScreen: (Uri) -> Unit = {}
) {
    val context = LocalContext.current
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var videoList by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val currentUser by uploadViewModel.currentUser
    var loggedIn by remember { mutableStateOf(false) }

    LaunchedEffect(currentUser) {
        loggedIn = currentUser != null
    }

    // Load videos when sheet opens
    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            videoList = uploadViewModel.loadDeviceVideos(context)
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
    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO
        )
    )
    LaunchedEffect(key1 = Unit) {
        if (!multiplePermissionState.permissions[0].status.isGranted) {
            multiplePermissionState.launchMultiplePermissionRequest()
        }
    }

    val fileLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = {})

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            Column(modifier = Modifier.fillMaxSize()) {
                if (multiplePermissionState.permissions[0].status.isGranted) {
                    CameraPreview(
                        cameraOpenType,
                        onClickCancel = { navController.navigateUp() },
                        onClickOpenFile = {
                            fileLauncher.launch(pickVisualMediaRequest)
                        }

                    )
                } else {
                    CameraMicrophoneAccessPage(
                        multiplePermissionState.permissions[1].status.isGranted,
                        cameraOpenType,
                        onClickCancel = { navController.navigateUp() },
                        onClickOpenFile = {

//                    fileLauncher.launch(pickVisualMediaRequest)

                            if (hasPermission) {
                                showBottomSheet = true
                                println("ShowBottomSheet: $showBottomSheet")
                                videoList = uploadViewModel.loadDeviceVideos(context)
                            } else {
                                // Ask permission on click
                                permissionLauncher.launch(videoPermission)
                                Toast.makeText(
                                    context,
                                    "Allow storage permissions to upload media",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                    {
//                val permissionState = when (it) {
//                    PermissionType.CAMERA -> multiplePermissionState.permissions[1]
//                    PermissionType.MICROPHONE -> multiplePermissionState.permissions[1]
//                }
//                permissionState.apply {
//                    if (this.status.shouldShowRationale) {
//                        this.launchPermissionRequest()
//                    } else {
//                        context.openAppSetting()
//                    }
//                }
                    }
                }


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
}
