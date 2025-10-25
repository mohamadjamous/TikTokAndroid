package com.example.tiktokandroid.auth.presentation.view.email_signup

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
import com.example.tiktokandroid.auth.presentation.viewmodel.SignupViewModel

@Composable
fun EmailSignupScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    viewModel: SignupViewModel = hiltViewModel()
) {

    var currentScreen by remember { mutableStateOf(EmailAuthScreen.Email) }

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
                    slideInHorizontally(
                        initialOffsetX = { if (targetState.ordinal > initialState.ordinal) it else -it }
                    ) + fadeIn() togetherWith
                            slideOutHorizontally(
                                targetOffsetX = { if (targetState.ordinal > initialState.ordinal) -it else it }
                            ) + fadeOut()
                },
                label = "Auth Flow Animation"
            ) { screen ->
                when (screen) {
                    EmailAuthScreen.Email -> EmailView(
                        onContinueClick = { currentScreen = EmailAuthScreen.Password },
                        onBackPressed = onBackPressed,
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )

                    EmailAuthScreen.Password -> PasswordView(
                        onContinueClick = { currentScreen = EmailAuthScreen.DOB },
                        onBackPressed = { currentScreen = EmailAuthScreen.Email },
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )

                    EmailAuthScreen.DOB -> DobView(
                        onContinueClick = { currentScreen = EmailAuthScreen.Username },
                        onBackPressed = { currentScreen = EmailAuthScreen.Password },
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel
                    )

                    EmailAuthScreen.Username -> UserNameView()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun EmailSignupScreenPreview() {
    EmailSignupScreen()
}