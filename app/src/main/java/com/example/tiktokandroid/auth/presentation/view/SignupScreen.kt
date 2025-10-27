package com.example.tiktokandroid.auth.presentation.view

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import com.example.tiktokandroid.R
import com.example.tiktokandroid.auth.data.model.AuthUiState
import com.example.tiktokandroid.auth.presentation.viewmodel.SignupViewModel
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.core.presentation.components.LoginButton
import com.example.tiktokandroid.core.presentation.components.PhoneNumberTextField
import com.example.tiktokandroid.core.presentation.model.Country
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokRed


@OptIn(UnstableApi::class)
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = hiltViewModel(),
    navigateToEmailSignup: () -> Unit = {},
    navigateToPhoneSignup: (String) -> Unit = {}
) {

    val countries by viewModel.countries.collectAsState()
    var phone by rememberSaveable { mutableStateOf("") }
    var fullPhoneNumber by rememberSaveable { mutableStateOf("") }
    var country by remember { mutableStateOf(Country()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }

    val state by viewModel.uiState.collectAsState()
    val phoneState by viewModel.phoneState.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(state) {
        when (state) {
            is AuthUiState.Idle -> { /* Show initial screen */
            }

            is AuthUiState.Loading -> {
                error = false
                loading = true
            }

            is AuthUiState.Success -> {
                loading = false
                error = false

//                navigateToPhoneSignup()
                viewModel.resetUiState()
            }

            is AuthUiState.Error -> {
                loading = false
                error = true
            }
        }
    }

    LaunchedEffect(phoneState) {
        when (phoneState) {
            is AuthUiState.Idle -> { /* Show initial screen */
            }

            is AuthUiState.Loading -> {
                error = false
                loading = true
            }

            is AuthUiState.Success -> {
                loading = false
                error = false

                navigateToPhoneSignup(fullPhoneNumber)
                viewModel.resetUiState()
            }

            is AuthUiState.Error -> {
                loading = false
                error = true
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

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


                if (error) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "phone number is not valid",
                        color = TikTokRed
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                }

                PhoneNumberTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    countries = countries,
                    onTextChange = {
                        phone = it
                    },
                    onCountryChange = {
                        country = it
                    }
                )

                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(top = 20.dp),
                    text = "Continue",
                    containerColor = TikTokRed,
                    contentColor = Color.White,
                    loading = loading,
                    onClick = {

                        fullPhoneNumber = "+${country.code}$phone"

                        if (viewModel.isPhoneNumberValid(number = fullPhoneNumber)) {
                            viewModel.checkPhoneNumber(
                                number = "${country.code} + $phone"
                            )
                        } else {
                            error = true
                            loading = false
                        }

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
}

@Preview(showSystemUi = true)
@Composable
private fun SignupScreenPreview() {
    SignupScreen()
}