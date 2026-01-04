package com.example.tiktokandroid.profile.presentation.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiktokandroid.R
import com.example.tiktokandroid.auth.presentation.components.SettingItem
import com.example.tiktokandroid.core.presentation.components.BackButton
import com.example.tiktokandroid.core.presentation.model.Screen
import com.example.tiktokandroid.theme.WhiteLightDimBg

@Preview(showSystemUi = true)
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    email: String = "123",
    phoneNumber: String = "123",
    dob: String = "123",
    onBackPressed: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WhiteLightDimBg)
            .padding(horizontal = 10.dp)
    ) {

        BackButton(
            modifier = Modifier.padding(top = 40.dp, start = 10.dp)
        ) { onBackPressed() }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            modifier = Modifier
                .padding(start = 40.dp),
            text = stringResource(R.string.account_information),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )


        Spacer(modifier = Modifier.height(40.dp))


        SettingItem(
            text = stringResource(R.string.email),
            iconVisible = false,
            buttonVisible = false,
            onItemClick = {

            },
            clickableItem = false,
            secondText = email,
            secondTextVisible = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        SettingItem(
            text = stringResource(R.string.phone_number),
            iconVisible = false,
            buttonVisible = false,
            clickableItem = false,
            secondText = phoneNumber,
            secondTextVisible = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        SettingItem(
            text = stringResource(R.string.dob),
            iconVisible = false,
            buttonVisible = false,
            clickableItem = false,
            secondText = dob,
            secondTextVisible = true
        )


    }


}