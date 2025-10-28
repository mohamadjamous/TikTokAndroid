package com.example.tiktokandroid.auth.presentation.view.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.core.presentation.components.CustomTextField
import com.example.tiktokandroid.core.presentation.components.PhoneNumberTextField
import com.example.tiktokandroid.core.presentation.model.Country
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokRed


@Composable
fun EmailLoginView(
    modifier: Modifier = Modifier) {

    var email by rememberSaveable { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("phone number is not valid") }

//    val state by viewModel.uiState.collectAsState()
//    val phoneState by viewModel.phoneState.collectAsState()
    val context = LocalContext.current

    Column (
        modifier = modifier.fillMaxSize()
            .background(Color.White)
            .padding(start = 20.dp,
                end = 20.dp,
                top = 20.dp,
                bottom = 55.dp)
    ){
        CustomTextField(
            hint = "Email address",
            onTextChange = {
                email = it
            }
        )

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


//                if (viewModel.isPhoneNumberValid(number = fullPhoneNumber)) {
//                    viewModel.checkPhoneNumber(number = fullPhoneNumber)
//                } else {
//                    error = true
//                    loading = false
//                    errorMessage = "phone number is not valid"
//                }

            }
        )
    }

}

@Preview(showSystemUi = true)
@Composable
private fun PhoneLoginViewPreview() {
    EmailLoginView(
    )
}