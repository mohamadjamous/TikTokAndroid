package com.example.tiktokandroid.auth.presentation.view.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.auth.data.model.AuthUiState
import com.example.tiktokandroid.auth.presentation.viewmodel.LoginViewModel
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.core.presentation.components.CustomLoadingView
import com.example.tiktokandroid.core.presentation.components.OtpTextField
import com.example.tiktokandroid.core.presentation.components.PhoneNumberTextField
import com.example.tiktokandroid.core.presentation.components.ResendCodeTimer
import com.example.tiktokandroid.core.presentation.model.Country
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.core.sharedpreferences.UserPreferences
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokRed
import com.example.tiktokandroid.utils.Common

@Composable
fun PhoneLoginView(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {

    var number by rememberSaveable { mutableStateOf("") }
    var fullPhoneNumber by rememberSaveable { mutableStateOf("") }
    var country by remember { mutableStateOf(Country()) }
    var loading by remember { mutableStateOf(false) }
    var loadingScreen by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("phone number is not valid") }
    val countries by viewModel.countries.collectAsState()
    var isPhoneView by remember { mutableStateOf(true) }

    var error by rememberSaveable { mutableStateOf(false) }
    var startTimer by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(false) }
    val phoneState by viewModel.phoneState.collectAsState()
    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current

    val otpUiState by viewModel.otpState.collectAsState()
    val sendCodeState by viewModel.sendCodeState.collectAsState()

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

                if (((phoneState as AuthUiState.Success).data as? Boolean) == true) {
                    isPhoneView = false
                    loadingScreen = true
                    startTimer = true
                    enabled = false
                    // send otp code
                    viewModel.sendOtpCode(number = fullPhoneNumber)
                } else {
                    isPhoneView = true
                    loadingScreen = false
                    error = true
                    errorMessage = "phone number does exists"
                }

                viewModel.resetUiState()
            }

            is AuthUiState.Error -> {
                loading = false
                error = true
                errorMessage = "phone number is not valid"
            }
        }
    }

    LaunchedEffect(sendCodeState) {
        when (sendCodeState) {
            is AuthUiState.Idle -> {

            }

            is AuthUiState.Loading -> {
                loadingScreen = true
                error = false
            }

            is AuthUiState.Success -> {
                loadingScreen = false
                error = false

                Toast.makeText(context, "OTP code was sent", Toast.LENGTH_SHORT).show()
                startTimer = true
                enabled = false
                isPhoneView = false
                // update user phone number
                viewModel.resetUiState()
            }

            is AuthUiState.Error -> {
                loadingScreen = false
                error = true

                startTimer = true
                enabled = false
            }
        }
    }

    LaunchedEffect(otpUiState) {
        when (otpUiState) {
            is AuthUiState.Idle -> {

            }

            is AuthUiState.Loading -> {
                loadingScreen = true
                error = false
            }

            is AuthUiState.Success -> {
                loadingScreen = false
                error = false

                // saver user to shared preferences
                viewModel.phoneLogin(fullPhoneNumber)
            }

            is AuthUiState.Error -> {
                loadingScreen = false
                error = true
            }
        }
    }

    LaunchedEffect(loginState) {
        when (loginState) {
            is AuthUiState.Idle -> {
                // Do nothing
            }

            is AuthUiState.Loading -> {
                loadingScreen = true
                error = false
            }

            is AuthUiState.Success -> {
                loadingScreen = false
                error = false

                val user = ((loginState as AuthUiState.Success).data as User)
                UserPreferences(context).saveUser(
                    user.id,
                    user.username,
                    user.dob,
                    user.phone,
                    user.email
                )

                Common.restartApp(context)
                viewModel.resetUiState()
            }
            is AuthUiState.Error -> {
                loadingScreen = false
                error = true
                errorMessage = "Error logging in"
            }
        }
    }


    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        Column(
            modifier = modifier
                .background(Color.White)
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = 55.dp
                )
        )
        {

            if (isPhoneView) {

                if (error) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = errorMessage,
                        color = TikTokRed
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                PhoneNumberTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    countries = countries,
                    onTextChange = {
                        number = it
                    },
                    onCountryChange = {
                        country = it
                    }
                )


            } else {
                Column()
                {
                    Spacer(modifier = Modifier.height(30.dp))

                    Column {
                        Text(
                            text = "Enter 6-digit code",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "Your code was sent to $fullPhoneNumber",
                            fontSize = 15.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (error) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = errorMessage,
                            color = TikTokRed
                        )
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    OtpTextField(
                        modifier = Modifier.padding(end = 20.dp),
                        onOtpComplete = { otpCode ->
                            viewModel.verifyOtp(otpCode)
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ResendCodeTimer(
                            totalTimeSeconds = 60,
                            start = startTimer,
                            enabled = enabled,
                            onTimerFinish = {
                                startTimer = false
                                enabled = true
                            },
                            onClick = {
                                // Resend otp action
                                viewModel.sendOtpCode(number = fullPhoneNumber)
                            }
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        TextButton(
                            onClick = {
                                isPhoneView = true
                            }
                        ) {

                            Text(
                                text = "change number",
                                fontSize = 15.sp,
                                color = Color.Blue
                            )

                        }
                    }


                }

            }


            Spacer(modifier = Modifier.weight(1f))

            if (isPhoneView) {

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

                        fullPhoneNumber = "+${country.code}$number"

                        if (viewModel.isPhoneNumberValid(number = fullPhoneNumber)) {
                            viewModel.checkPhoneNumber(number = fullPhoneNumber)
                        } else {
                            error = true
                            loading = false
                            errorMessage = "phone number is not valid"
                        }

                    }

                )
            }
        }
        if (loadingScreen) {
            CustomLoadingView()
        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun PhoneLoginViewPreview() {
    PhoneLoginView(

    )
}