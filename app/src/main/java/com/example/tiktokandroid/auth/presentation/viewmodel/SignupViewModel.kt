package com.example.tiktokandroid.auth.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktokandroid.core.presentation.model.Country
import com.example.tiktokandroid.core.presentation.model.CountryJsonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.InputStreamReader


@HiltViewModel
class SignupViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries = _countries.asStateFlow()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {

            val countriesList = parseCountriesJson()

            val mappedList = countriesList.map { country ->
                Country(
                    name = country.country,
                    code = country.code,
                    iso = country.iso,
                )
            }

            _countries.value = mappedList


        }
    }


    private fun parseCountriesJson(): List<CountryJsonResponse> {
        val inputStream = context.assets.open("countries.json")
        val jsonString = InputStreamReader(inputStream).readText()
        return Json.decodeFromString(jsonString)
    }

}