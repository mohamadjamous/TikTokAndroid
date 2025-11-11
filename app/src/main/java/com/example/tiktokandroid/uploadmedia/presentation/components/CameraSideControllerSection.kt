package com.example.tiktokandroid.uploadmedia.presentation.components

import androidx.camera.core.CameraSelector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.CameraController

@Composable
fun CameraSideControllerSection(
    modifier: Modifier,
    defaultCameraFacing: CameraSelector,
    onClickController: (CameraController) -> Unit
) {
    val controllers =
        if (defaultCameraFacing == CameraSelector.DEFAULT_BACK_CAMERA) CameraController.values()
            .toMutableList().apply { remove(CameraController.MIRROR) }
        else CameraController.values().toMutableList().apply { remove(CameraController.FLASH) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        controllers.forEach {
            ControllerItem(it, onClickController)
        }
    }
}
