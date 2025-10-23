package com.example.tiktokandroid.auth.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiktokandroid.R
import com.example.tiktokandroid.core.presentation.components.LoginButton

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onEmailUsernameClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {}
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Login in",
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
            LoginButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Use phone / email / username",
                leadingPainter = rememberVectorPainter(image = Icons.Outlined.Person),
                onClick = { onEmailUsernameClick() }
            )

            Spacer(modifier = Modifier.height(15.dp))


            LoginButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Continue with Google",
                leadingPainter = painterResource(R.drawable.google_icon),
                onClick = { onGoogleClick() }
            )

        }




    }

}


@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}