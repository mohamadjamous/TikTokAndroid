package com.example.tiktokandroid.auth.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.auth.presentation.viewmodel.SettingsViewModel
import com.example.tiktokandroid.core.presentation.components.BackButton
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokRed
import com.example.tiktokandroid.utils.Common

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        BackButton(
            modifier = Modifier.padding(top = 40.dp, start = 10.dp)
        ) {
            onBackPressed()
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            modifier = Modifier.padding(start = 30.dp),
            text = "Settings and privacy",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(25.dp))


        Column(
            modifier = Modifier.padding(start = 30.dp),
        ) {
            Text(
                modifier = Modifier.clickable{
                    viewModel.logout()
                    Common.restartApp(context)
                },
                text = "Logout",
                color = TikTokRed,
            )
        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen()
}