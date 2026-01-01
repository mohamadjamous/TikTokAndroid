package com.example.tiktokandroid.auth.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.R
import com.example.tiktokandroid.auth.presentation.components.SettingItem
import com.example.tiktokandroid.auth.presentation.viewmodel.SettingsViewModel
import com.example.tiktokandroid.core.presentation.components.BackButton
import com.example.tiktokandroid.theme.Gray
import com.example.tiktokandroid.theme.WhiteLightDimBg
import com.example.tiktokandroid.utils.Common

@Preview(showSystemUi = true)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    onDisplayPressed: () -> Unit = {},
    onLanguagePressed: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val currentUser by viewModel.currentUser

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WhiteLightDimBg)
    ) {

        BackButton(
            modifier = Modifier.padding(top = 40.dp, start = 10.dp)
        ) {
            onBackPressed()
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            modifier = Modifier.padding(start = 40.dp),
            text = stringResource(R.string.settings_and_privacy),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(25.dp))


        Column(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
        ) {

            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = stringResource(R.string.account),
                color = Gray
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingItem(
                text = stringResource(R.string.my_account),
                icon = Icons.Filled.Person,
                buttonText = stringResource(R.string.sign_up),
                buttonVisible = true,
                onButtonClick = {

                }
            )

            Spacer(modifier = Modifier.height(40.dp))


            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = stringResource(R.string.content_display),
                color = Gray
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingItem(
                text = stringResource(R.string.display),
                icon = Icons.Filled.Bedtime,
                buttonVisible = false,
                onItemClick = {
                    onDisplayPressed()
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            SettingItem(
                text = stringResource(R.string.language),
                icon = Icons.Filled.Language,
                buttonVisible = false,
                onItemClick = {
                    onLanguagePressed()
                }
            )

            Spacer(modifier = Modifier.height(40.dp))


            if (currentUser != null) {

                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = stringResource(R.string.other),
                    color = Gray
                )

                Spacer(modifier = Modifier.height(10.dp))


                SettingItem(
                    text = stringResource(R.string.logout),
                    icon = Icons.Filled.Logout,
                    buttonVisible = false,
                    onItemClick = {
                        println("OnLogoutClick")
                        viewModel.logout()
                        Common.restartApp(context)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }


        }

    }

}


