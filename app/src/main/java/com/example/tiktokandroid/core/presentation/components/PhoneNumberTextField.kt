package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.core.presentation.model.Country

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    hint: String = "Phone number",
    backgroundColor: Color = Color.LightGray,
    onTextChange: (String) -> Unit = {},
    onCountryChange: (Country) -> Unit = {},
    countries: List<Country>
) {
    var phoneNumber by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf(Country()) }

    Column(modifier = modifier) {

        Text(
            text = "Phone Number",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start,
            color = Color(0xff76a9ff),
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Country Picker Dropdown
            Box {
                TextButton(
                    onClick = { expanded = true },
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Text(selectedCountry.displayText, color = Color.Black)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select country"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    countries.forEach { country ->
                        DropdownMenuItem(
                            text = { Text(country.itemText) },
                            onClick = {
                                selectedCountry = country
                                expanded = false
                                onCountryChange(country)
                            }
                        )
                    }
                }
            }

            // Phone number input
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Transparent),
                value = phoneNumber,
                onValueChange = {
                    if (it.length <= 15) {
                        phoneNumber = it
                        onTextChange(it)
                    }
                },
                placeholder = { Text(hint, color = Color.Gray) },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview
@Composable
private fun CustomTextFieldPreview() {
    PhoneNumberTextField(
        countries = emptyList()
    )
}