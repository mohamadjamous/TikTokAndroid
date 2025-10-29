package com.example.tiktokandroid.auth.presentation.view.login

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.auth.data.model.AuthUiState
import com.example.tiktokandroid.auth.presentation.viewmodel.LoginViewModel
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.core.presentation.components.CustomLoadingView
import com.example.tiktokandroid.core.presentation.components.CustomTextField
import com.example.tiktokandroid.core.presentation.components.PasswordTextField
import com.example.tiktokandroid.core.presentation.components.PhoneNumberTextField
import com.example.tiktokandroid.core.presentation.model.Country
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokRed
import com.example.tiktokandroid.utils.Common


@Composable
fun EmailLoginView(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var isPassword by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("email addres does not exist") }
    var loadingScreen by remember { mutableStateOf(false) }
    val loginState by viewModel.loginState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (uiState) {
            is AuthUiState.Idle -> { /* Show initial screen */
            }

            is AuthUiState.Loading -> {
                error = false
                loading = true
                loadingScreen = false
            }

            is AuthUiState.Success -> {
                loading = false
                loadingScreen = false
                error = false

                if (((uiState as AuthUiState.Success).data as? Boolean) == true) {

                    // show password text field
                    isPassword = true
                    loadingScreen = false
                    loading = false

                } else {
                    isPassword = false
                    loadingScreen = false
                    error = true
                    errorMessage = "email address does exists"
                }

                viewModel.resetUiState()
            }

            is AuthUiState.Error -> {
                loading = false
                loadingScreen = false
                error = true
                errorMessage = "phone number is not valid"
            }
        }
    }

    LaunchedEffect(loginState) {
        when (loginState) {
            is AuthUiState.Idle -> {

            }

            is AuthUiState.Loading -> {
                loadingScreen = true
                error = false
                loading = false
            }

            is AuthUiState.Success -> {
                loadingScreen = false
                error = false
                loading = false

                // restart app
                Common.restartApp(context)

                // update user phone number
                viewModel.resetUiState()
            }

            is AuthUiState.Error -> {
                loadingScreen = false
                error = true
                loading = false
                errorMessage = "error logging in, try again later!"
            }
        }
    }


    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = 55.dp
                )
        )
        {
            if (error) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = errorMessage,
                    color = TikTokRed
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            CustomTextField(
                hint = "Email address",
                onTextChange = {
                    email = it
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (isPassword) {
                PasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    hint = "Enter your password",
                    onTextChange = {
                        password = it
                    },
                    showPasswordLevels = false
                )
            }


            Spacer(modifier = Modifier.weight(1f))



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
                    if (!isPassword) {
                        if (viewModel.validateEmail(email)) {
                            error = false
                            loading = true
                            viewModel.checkEmail(email)

                        } else {
                            error = true
                            loading = false
                            loadingScreen = false
                            errorMessage = "email is not valid"
                        }
                    }else{
                        if (!password.isEmpty()){

                            loading = false
                            loadingScreen = true
                            error = false
                            viewModel.emailLogin(email, password)

                        }else{
                            errorMessage = "password cannot be empty"
                            loading = false
                            loadingScreen = false
                            error = true
                        }
                    }

                }
            )
        }

        if (loadingScreen) {
            CustomLoadingView()
        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun PhoneLoginViewPreview() {
    EmailLoginView(
    )
}