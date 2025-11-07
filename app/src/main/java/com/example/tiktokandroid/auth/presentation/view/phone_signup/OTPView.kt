package com.example.tiktokandroid.auth.presentation.view.phone_signup

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.auth.data.model.AuthUiState
import com.example.tiktokandroid.auth.presentation.viewmodel.SignupViewModel
import com.example.tiktokandroid.core.presentation.components.BackButton
import com.example.tiktokandroid.core.presentation.components.CustomLoadingView
import com.example.tiktokandroid.core.presentation.components.OtpTextField
import com.example.tiktokandroid.core.presentation.components.ResendCodeTimer
import com.example.tiktokandroid.theme.PrimaryColor
import kotlinx.coroutines.delay

@Composable
fun OTPView(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    onContinueClick: () -> Unit = {},
    number: String = "",
    viewModel: SignupViewModel
) {

    var otp by rememberSaveable { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }
    var startTimer by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(false) }


    val otpUiState by viewModel.otpState.collectAsState()
    val sendCodeState by viewModel.sendCodeState.collectAsState()

    val context = LocalContext.current

    // On Composition
    LaunchedEffect(key1 = Unit) {
        viewModel.sendOtpCode(number = number)
    }

    LaunchedEffect(sendCodeState) {
        when (sendCodeState) {
            is AuthUiState.Idle -> {

            }

            is AuthUiState.Loading -> {
                loading = true
                isError = false
            }

            is AuthUiState.Success -> {
                loading = false
                isError = false

                Toast.makeText(context, "OTP code was sent", Toast.LENGTH_SHORT).show()
                startTimer = true
                enabled = false
                // update user phone number
                viewModel.resetUiState()
            }

            is AuthUiState.Error -> {
                loading = false
                isError = true

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
                loading = true
                isError = false
            }

            is AuthUiState.Success -> {
                loading = false
                isError = false

                // update user phone number
                viewModel.onPhoneNumberChange(newNumber = number)
                onContinueClick()
                viewModel.resetUiState()
            }

            is AuthUiState.Error -> {
                loading = false
                isError = true
            }
        }
    }




    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = modifier.fillMaxSize()
        )
        {

            BackButton(
                modifier = Modifier.padding(top = 40.dp, start = 10.dp)
            ) {
                onBackPressed()
            }

            Spacer(modifier = Modifier.height(30.dp))

            Column {
                Text(
                    modifier = Modifier.padding(start = 30.dp),
                    text = "Enter 6-digit code",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    modifier = Modifier.padding(start = 30.dp),
                    text = "Your code was sent to $number",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (isError) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp),
                    text = "otp not valid",
                    color = PrimaryColor
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            OtpTextField(
                modifier = Modifier.padding(start = 30.dp, end = 20.dp),
                onOtpComplete = { otpCode ->
                    otp = otpCode
                    viewModel.verifyOtp(otpCode)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            ResendCodeTimer(
                modifier = Modifier.padding(start = 30.dp),
                totalTimeSeconds = 60,
                start = startTimer,
                enabled = enabled,
                onTimerFinish = {
                    startTimer = false
                    enabled = true
                },
                onClick = {
                    // Resend otp action
                    viewModel.sendOtpCode(number = number)
                }
            )


        }

        if (loading) {
            CustomLoadingView()
        }

    }

}


@Preview(showSystemUi = true)
@Composable
private fun EmailSignupScreenPreview() {
    OTPView(
        number = "12314",
        viewModel = hiltViewModel()
    )
}