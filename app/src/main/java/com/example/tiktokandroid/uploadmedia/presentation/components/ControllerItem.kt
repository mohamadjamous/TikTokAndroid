package com.example.tiktokandroid.uploadmedia.presentation.components

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
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
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.CameraController

@Composable
fun ControllerItem(
    cameraController: CameraController, onClickController: (CameraController) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(
        4.dp, alignment = Alignment.CenterVertically
    ), horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
        onClickController(cameraController)
    }) {
        Icon(
            painterResource(id = cameraController.icon),
            contentDescription = null,
            modifier = Modifier.size(27.dp),
            tint = Color.White.copy(alpha = 0.8f)
        )
        Text(
            text = stringResource(id = cameraController.title),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

val pickVisualMediaRequest by lazy {
    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
}

fun alphaForInteractiveView(isEnabledLayout: Boolean): Float = if (isEnabledLayout) 1f else 0.28f

