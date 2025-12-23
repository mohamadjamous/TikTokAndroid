package com.example.tiktokandroid.auth.presentation.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.R
import com.example.tiktokandroid.auth.presentation.viewmodel.SettingsViewModel
import com.example.tiktokandroid.core.presentation.components.BackButton
import com.example.tiktokandroid.theme.Gray
import com.example.tiktokandroid.theme.White
import com.example.tiktokandroid.theme.WhiteLightDimBg


@Preview(showSystemUi = true)
@Composable
fun DisplayScreen(
    modifier: Modifier = Modifier,
    darkMode: Boolean = true,
    onBackPressed: () -> Unit = {},
    onThemeChange: (Boolean) -> Unit = {}
) {

    var lightSelected by remember { mutableStateOf(!darkMode) }
    var darkSelected by remember { mutableStateOf(darkMode) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WhiteLightDimBg)
    ) {

        BackButton(
            modifier = Modifier.padding(top = 40.dp, start = 10.dp)
        ) { onBackPressed() }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            modifier = Modifier
                .padding(start = 40.dp),
            text = stringResource(R.string.display),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )


        Spacer(modifier = Modifier.height(40.dp))

        Text(
            modifier = Modifier.padding(start = 15.dp),
            text = stringResource(R.string.appearance),
            color = Gray
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 15.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(White),
            horizontalArrangement = Arrangement.Center
        )
        {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                Icon(
                    modifier = Modifier.height(50.dp).width(30.dp),
                    imageVector = Icons.Default.LightMode,
                    contentDescription = null
                )

                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.light),
                    style = MaterialTheme.typography.titleSmall
                )

                Switch(
                    checked = lightSelected,
                    onCheckedChange = {
                        lightSelected = it
                        darkSelected = !lightSelected
                        if (it) onThemeChange(false)
                    }
                )



            }


            Spacer(modifier = Modifier.width(80.dp))


            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                Icon(
                    modifier = Modifier.height(50.dp).width(30.dp),
                    imageVector = Icons.Default.DarkMode,
                    contentDescription = null
                )

                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.dark),
                    style = MaterialTheme.typography.titleSmall
                )

                Switch(
                    checked = darkSelected,
                    onCheckedChange = {
                        darkSelected = it
                        lightSelected = !darkSelected
                        if (it) onThemeChange(true)
                    }
                )



            }

        }



    }


}