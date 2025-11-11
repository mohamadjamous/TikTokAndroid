package com.example.tiktokandroid.uploadmedia.presentation.components

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.tiktokandroid.R
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.CameraController
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.Tabs

@Composable
fun CameraPreview(
    cameraOpenType: Tabs,
    onClickCancel: () -> Unit,
    onClickOpenFile: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var defaultCameraFacing by remember { mutableStateOf(CameraSelector.DEFAULT_FRONT_CAMERA) }
    val cameraProvider = cameraProviderFuture.get()
    val preview = remember { Preview.Builder().build() }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                val cameraPreview = PreviewView(it)
                cameraProviderFuture.addListener({
                    preview.also {
                        it.setSurfaceProvider(cameraPreview.surfaceProvider)
                    }
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(lifecycleOwner, defaultCameraFacing, preview)
                    } catch (e: Exception) {
                        Log.e("camera", "camera preview exception :${e.message}")
                    }
                }, ContextCompat.getMainExecutor(context))
                cameraPreview
            }, modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        ) {
            FooterCameraController(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                cameraOpenType = cameraOpenType,
                onClickEffect = { },
                onClickOpenFile = onClickOpenFile,
                onclickCameraCapture = { },
                isEnabledLayout = true
            )


            CameraSideControllerSection(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp),
                defaultCameraFacing
            ) {
                when (it) {
                    CameraController.FLIP -> {
                        defaultCameraFacing =
                            if (defaultCameraFacing == CameraSelector.DEFAULT_FRONT_CAMERA) CameraSelector.DEFAULT_BACK_CAMERA else CameraSelector.DEFAULT_FRONT_CAMERA
                    }
                    else -> {}
                }
            }

            LaunchedEffect(key1 = defaultCameraFacing) {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, defaultCameraFacing, preview
                )
            }

            Icon(painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp)
                    .size(24.dp)
                    .clickable {
                        onClickCancel()
                    })

            Row(
                modifier = Modifier.align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_music_note),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
                Text(
                    text = stringResource(id = R.string.add_sound),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }

}
