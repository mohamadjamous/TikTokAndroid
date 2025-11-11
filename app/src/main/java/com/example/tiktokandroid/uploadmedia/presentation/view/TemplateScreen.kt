package com.example.tiktokandroid.uploadmedia.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tiktokandroid.R
import com.example.tiktokandroid.feed.presentation.components.LargeSpace
import com.example.tiktokandroid.uploadmedia.presentation.components.CustomButton
import com.example.tiktokandroid.uploadmedia.presentation.components.TemplatePager
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.CameraMediaEvent
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.CameraMediaViewModel

/**
 * Created by Puskal Khadka on 4/2/2023.
 */

@Composable
fun TemplateScreen(
    navController: NavController,
    viewModel: CameraMediaViewModel
) {
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.onTriggerEvent(CameraMediaEvent.EventFetchTemplate)
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 20.dp, start = 6.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }

        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            viewState?.templates?.let {
                TemplatePager(it)
            }
        }
        CustomButton(
            buttonText = stringResource(id = R.string.upload_photos),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(0.65f)
        ) {

        }
        LargeSpace()
    }

}