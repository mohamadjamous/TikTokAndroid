package com.example.tiktokandroid.auth.presentation.view.email_signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.R
import com.example.tiktokandroid.auth.presentation.viewmodel.SignupViewModel
import com.example.tiktokandroid.core.presentation.components.BackButton
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.core.presentation.components.Picker
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokRed

@Composable
fun DobView(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    onContinueClick: () -> Unit = {},
    viewModel: SignupViewModel
) {
    var input by rememberSaveable { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }

    var day by remember { mutableStateOf("1") }
    var month by remember { mutableStateOf("January") }
    var year by remember { mutableStateOf("2000") }

    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        BackButton(
            modifier = Modifier.padding(top = 40.dp, start = 10.dp)
        ) {
            onBackPressed()
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    modifier = Modifier.padding(start = 30.dp),
                    text = "When's your birthday",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    modifier = Modifier.padding(start = 30.dp),
                    text = "Your birthday won't be shown publicly.",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.weight(1f))


            Icon(
                modifier = Modifier.size(55.dp),
                painter = painterResource(R.drawable.birthday_cake),
                contentDescription = "birthday",
                tint = Color.Unspecified
            )

        }

        Spacer(modifier = Modifier.height(5.dp))

        if (isError) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                text = "please provide birthday",
                color = TikTokRed
            )
        }

        Spacer(modifier = Modifier.height(25.dp))


        TextField(
            modifier = Modifier.padding(start = 30.dp, end = 20.dp).fillMaxWidth(),
            onValueChange = {},
            value = "$day $month, $year"
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            text = "Next",
            containerColor = TikTokRed,
            contentColor = Color.White,
            loading = loading,
            onClick = {
                val dob = "$day $month, $year"
                if (!dob.isEmpty()) {
                    isError = false
                    viewModel.onDobChange(dob)
                    onContinueClick()
                }else{
                    isError = true
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        {
            Row(horizontalArrangement = Arrangement.Center) {
                Picker((1..31).map { it.toString() }, day, { day = it })
                Spacer(Modifier.width(12.dp))
                Picker(months, month, { month = it })
                Spacer(Modifier.width(12.dp))
                Picker((1950..2025).map { it.toString() }, year, { year = it })
            }
        }

    }
}






@Preview(showSystemUi = true)
@Composable
private fun DobPreview() {

    DobView(
        viewModel = hiltViewModel()
    )
}