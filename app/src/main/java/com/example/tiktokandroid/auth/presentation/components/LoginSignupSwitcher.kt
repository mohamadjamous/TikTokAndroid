package com.example.tiktokandroid.auth.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.auth.presentation.view.LoginScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginSignupSwitcher(
    modifier : Modifier = Modifier
) {
    var showLogin by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Content area (takes remaining space)
        Box(
            modifier = Modifier
                .weight(1f) // ✅ makes space for bottom bar
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = showLogin,
                transitionSpec = {
                    slideInHorizontally(initialOffsetX = { if (targetState) it else -it }) + fadeIn() togetherWith
                            slideOutHorizontally(targetOffsetX = { if (targetState) -it else it }) + fadeOut()
                },
                label = "Login/Signup Animation"
            ) { isLogin ->
                if (isLogin) {
                    LoginScreen(modifier = Modifier.fillMaxSize())
                } else {
                    LoginScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }

        // Bottom bar always visible
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .clickable { showLogin = !showLogin },
                text = if (showLogin)
                    "Don't have an account? Sign up"
                else
                    "Already have an account? Log in",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
private fun LoginSwitcherPreview() {
    LoginSignupSwitcher()
}