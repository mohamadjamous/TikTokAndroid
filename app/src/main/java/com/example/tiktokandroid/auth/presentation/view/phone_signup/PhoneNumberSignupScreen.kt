package com.example.tiktokandroid.auth.presentation.view.phone_signup

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.auth.data.model.EmailAuthScreen
import com.example.tiktokandroid.auth.presentation.view.email_signup.DobView
import com.example.tiktokandroid.auth.presentation.view.email_signup.EmailView
import com.example.tiktokandroid.auth.presentation.view.email_signup.PasswordView
import com.example.tiktokandroid.auth.presentation.view.email_signup.UserNameView
import com.example.tiktokandroid.auth.presentation.viewmodel.SignupViewModel

@Composable
fun PhoneNumberSignupScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    viewModel: SignupViewModel = hiltViewModel()
) {

    var currentScreen by remember { mutableStateOf(EmailAuthScreen.Email) }
    var isForward by remember { mutableStateOf(true) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                    // Use the externally tracked direction
                    if (isForward) {
                        // Forward transition
                        slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) + fadeIn() togetherWith
                                slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut()
                    } else {
                        // Backward transition
                        slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) + fadeIn() togetherWith
                                slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) + fadeOut()
                    }
                },
                label = "Auth Flow Animation"
            ) { screen ->
                when (screen) {
                    EmailAuthScreen.Email -> EmailView(
                        onContinueClick = {
                            isForward = true
                            currentScreen = EmailAuthScreen.Password
                        },
                        onBackPressed = {
                            isForward = false
                            onBackPressed
                        },
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )

                    EmailAuthScreen.Password -> PasswordView(
                        onContinueClick = {
                            isForward = true
                            currentScreen = EmailAuthScreen.DOB
                        },
                        onBackPressed = {
                            isForward = false
                            currentScreen = EmailAuthScreen.Email
                        },
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )

                    EmailAuthScreen.DOB -> DobView(
                        onContinueClick = {
                            isForward = true
                            currentScreen = EmailAuthScreen.Username
                        },
                        onBackPressed = {
                            isForward = false
                            currentScreen = EmailAuthScreen.Password
                        },
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )

                    EmailAuthScreen.Username -> UserNameView(
                        onBackPressed = {
                            isForward = false
                            currentScreen = EmailAuthScreen.DOB
                        },
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun EmailSignupScreenPreview() {
    PhoneNumberSignupScreen()
}