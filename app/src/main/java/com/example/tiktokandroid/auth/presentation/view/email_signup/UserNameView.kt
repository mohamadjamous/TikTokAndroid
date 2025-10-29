package com.example.tiktokandroid.auth.presentation.view.email_signup

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
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.core.presentation.components.CustomTextField
import com.example.tiktokandroid.core.presentation.model.User
import com.example.tiktokandroid.core.sharedpreferences.UserPreferences
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokRed
import com.example.tiktokandroid.utils.Common

@Composable
fun UserNameView(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    emailSignup : Boolean = false,
    viewModel: SignupViewModel
) {

    var username by rememberSaveable { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(state) {
        when (state) {
            is AuthUiState.Idle -> { /* Show initial screen */
            }

            is AuthUiState.Loading -> {
                loading = true
            }

            is AuthUiState.Success -> {
                loading = false
                isError = false

                val user = ((state as AuthUiState.Success).data as User)
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
                println("ErrorMessage: ${(state as AuthUiState.Error).message}")
                loading = false
                isError = true
            }
        }
    }

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
            text = "Create nickname",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            modifier = Modifier.padding(start = 30.dp),
            text = "This can be anything you like and can be changed later. If you skip this step, you will be automatically assigned a default nickname",
            fontSize = 15.sp,
            color = Color.Gray
        )


        Spacer(modifier = Modifier.height(5.dp))

        if (isError) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                text = "error creating new user, try again!",
                color = TikTokRed
            )
        }

        Spacer(modifier = Modifier.height(15.dp))


        CustomTextField(
            modifier = Modifier.padding(start = 30.dp, end = 20.dp),
            hint = "Add your nickname",
            onTextChange = {
                username = it
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 65.dp),
            text = "Continue",
            containerColor = TikTokRed,
            contentColor = Color.White,
            loading = loading,
            onClick = {

                if (username.isEmpty()) {
                    // generate default username
                    viewModel.onUsernameChange(newUsername = viewModel.generateUsername())
                    if (emailSignup) viewModel.emailSignup() else viewModel.phoneNumberSignup()
                } else {
                    viewModel.onUsernameChange(newUsername = username)
                    if (emailSignup) viewModel.emailSignup() else viewModel.phoneNumberSignup()
                }


            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UserNamePreview() {

    UserNameView(
        viewModel = hiltViewModel()
    )
}