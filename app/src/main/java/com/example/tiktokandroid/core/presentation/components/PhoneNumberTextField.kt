package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
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
import com.example.tiktokandroid.feed.presentation.view.theme.TikTokLightGray
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    hint: String = "Phone number",
    backgroundColor: Color = TikTokLightGray,
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

                // Show progress indicator if countries empty or loading
                if (countries.isEmpty()) {

                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                } else {

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

                    if (expanded) {
                        var searchQuery by remember { mutableStateOf("") }

                        AlertDialog(
                            onDismissRequest = { expanded = false },
                            confirmButton = {},
                            text = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(400.dp)
                                ) {

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        IconButton(
                                            onClick = {
                                                expanded = false
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "close"
                                            )
                                        }
                                    }

                                    // Search Bar
                                    TextField(
                                        value = searchQuery,
                                        onValueChange = { searchQuery = it },
                                        placeholder = { Text("Search country...") },
                                        singleLine = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp),
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color(0xFFF5F5F5),
                                            unfocusedContainerColor = Color(0xFFF5F5F5),
                                            disabledContainerColor = Color(0xFFF5F5F5),
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )

                                    // Filtered Country List
                                    val filteredCountries = remember(searchQuery, countries) {
                                        countries.filter {
                                            it.itemText.contains(
                                                searchQuery,
                                                ignoreCase = true
                                            )
                                        }
                                    }

                                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                                        items(filteredCountries) { country ->
                                            Text(
                                                text = country.itemText,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        selectedCountry = country
                                                        expanded = false
                                                        onCountryChange(country)
                                                    }
                                                    .padding(12.dp)
                                            )
                                        }
                                    }
                                }
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