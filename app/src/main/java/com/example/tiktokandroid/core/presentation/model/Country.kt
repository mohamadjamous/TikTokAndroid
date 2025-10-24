package com.example.tiktokandroid.core.presentation.model

data class Country(
    val name: String = "",
    val code: String = "",
    val iso: String = "",
) {
    val displayText: String
        get() = "$iso +$code"

    val itemText: String
        get() = "$name +$code"
}
