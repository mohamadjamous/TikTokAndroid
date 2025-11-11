package com.example.tiktokandroid.uploadmedia.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.R
import com.example.tiktokandroid.feed.presentation.components.MediumSpace
import com.example.tiktokandroid.feed.presentation.components.Space
import com.example.tiktokandroid.theme.LightGreenColor
import com.example.tiktokandroid.theme.TealColor
import com.example.tiktokandroid.theme.White
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.PermissionType
import com.example.tiktokandroid.uploadmedia.presentation.viewmodel.Tabs

@Composable
fun CameraMicrophoneAccessPage(
    isMicrophonePermissionGranted: Boolean,
    cameraOpenType: Tabs,
    onClickCancel: () -> Unit,
    onClickOpenFile: () -> Unit,
    onClickGrantPermission: (PermissionType) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        TealColor, LightGreenColor
                    )
                )
            )
            .padding(top = 32.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(
            modifier = Modifier
                .padding(start = 16.dp)
                .size(24.dp)
                .align(Alignment.Start),
            onClick = {
                onClickCancel()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = null
            )
        }


        MediumSpace()

        Text(
            text = stringResource(id = R.string.allow_tiktok_to_access_your),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.75f)
        )
        MediumSpace()

        Text(
            text = "${stringResource(id = R.string.take_photos_record_videos_or_preview)} ${
                stringResource(id = R.string.you_can_change_preference_in_setting)
            }",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.75f),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )
        30.dp.Space()

        Card(
            modifier = Modifier.fillMaxWidth(0.75f), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterVertically)
            ) {
                Row(
                    modifier = Modifier.clickable {
                        onClickGrantPermission(PermissionType.CAMERA)
                    },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(id = R.string.access_camera),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (!isMicrophonePermissionGranted) {
                    Divider(color = White.copy(alpha = 0.2f))
                    Row(
                        modifier = Modifier.clickable {
                            onClickGrantPermission(PermissionType.MICROPHONE)
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_microphone),
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(id = R.string.access_microhpone),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

            }
        }
        Spacer(modifier = Modifier.weight(1f))
        FooterCameraController(
            cameraOpenType = cameraOpenType,
            onClickEffect = { },
            onClickOpenFile = onClickOpenFile,
            onclickCameraCapture = { },
            isEnabledLayout = false
        )
    }
}
