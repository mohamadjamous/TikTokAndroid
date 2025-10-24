package com.example.tiktokandroid.auth.presentation.view.email_signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.auth.presentation.viewmodel.SignupViewModel
import com.example.tiktokandroid.core.presentation.components.BackButton
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.core.presentation.components.CustomTextField
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokRed

@Composable
fun PasswordView(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    onContinueClick: () -> Unit = {},
    viewModel: SignupViewModel = hiltViewModel()
) {

    var input by rememberSaveable { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        BackButton(
            modifier = Modifier.padding(top = 40.dp, start = 10.dp)
        ){
            onBackPressed()
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            modifier = Modifier.padding(start = 30.dp),
            text = "Create password",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(20.dp))



        Spacer(modifier = Modifier.weight(1f))

        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            text = "Continue",
            containerColor = TikTokRed,
            contentColor = Color.White,
            loading = loading,
            onClick = {

            }
        )

    }

}

@Preview(showSystemUi = true)
@Composable
private fun EmailSignupScreenPreview() {
    EmailView()
}