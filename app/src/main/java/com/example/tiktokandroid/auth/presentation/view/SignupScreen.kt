package com.example.tiktokandroid.auth.presentation.view

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import com.example.tiktokandroid.R
import com.example.tiktokandroid.auth.presentation.viewmodel.SignupViewModel
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.core.presentation.components.LoginButton
import com.example.tiktokandroid.core.presentation.components.PhoneNumberTextField
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokRed
import kotlinx.coroutines.launch


@OptIn(UnstableApi::class)
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    viewModel : SignupViewModel = hiltViewModel(),
    navigateToEmailSignup: () -> Unit = {}
) {

    val countries by viewModel.countries.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Sign up",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            PhoneNumberTextField(
                modifier = Modifier.fillMaxWidth()
                .height(90.dp),
                countries = countries
            )

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(top = 20.dp),
                text = "Continue",
                containerColor = TikTokRed,
                contentColor = Color.White,
                onClick = {

                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            )
            {

                Divider(
                    modifier = Modifier.background(Color.Black)
                )

                Text(
                    modifier = Modifier.background(Color.White),
                    text = "    or    ",
                )

            }

            Spacer(modifier = Modifier.height(30.dp))


            LoginButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Continue with Email",
                leadingPainter = rememberVectorPainter(image = Icons.Filled.Email),
                onClick = {
                    navigateToEmailSignup()
                }
            )

            Spacer(modifier = Modifier.height(15.dp))


            LoginButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Continue with Google",
                leadingPainter = painterResource(R.drawable.google_icon),
                onClick = {
                    //    onGoogleClick()
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SignupScreenPreview() {
    SignupScreen()
}