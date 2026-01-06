package com.example.tiktokandroid.auth.presentation.view.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.R
import com.example.tiktokandroid.core.presentation.components.BackButton
import com.example.tiktokandroid.theme.Black
import com.example.tiktokandroid.theme.Gray
import com.example.tiktokandroid.theme.WhiteLightDimBg


@SuppressLint("UnrememberedMutableState")
@Preview(showSystemUi = true)
@Composable
fun LanguageScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
) {
    val languages = mutableStateListOf("English", "Arabic")
    var textColor by remember { mutableStateOf(Gray) }

    var selectedLanguage by remember { mutableStateOf(languages.firstOrNull()) }
    val doneButtonColor = if (selectedLanguage != null) Color.Black else Color.Gray


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WhiteLightDimBg)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            BackButton(
                modifier = Modifier.padding(top = 40.dp, start = 10.dp)
            ) { onBackPressed() }


            Text(
                modifier = Modifier.padding(top = 50.dp, end = 10.dp),
                text = stringResource(R.string.done),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                color = doneButtonColor // Color changes when language is selected
            )
        }

        Spacer(modifier = Modifier.height(15.dp))



        Column(
            modifier = Modifier.padding(start = 20.dp)
        ) {
            languages.forEach { lang ->
                val isSelected = lang == selectedLanguage

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedLanguage = lang }
                        .padding(vertical = 12.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = lang,
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isSelected) Black else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )

                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
        }



    }


}