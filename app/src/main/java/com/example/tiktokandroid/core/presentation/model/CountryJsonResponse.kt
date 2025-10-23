package com.example.tiktokandroid.core.presentation.model

import kotlinx.serialization.Serializable


@Serializable
data class CountryJsonResponse(
    val country: String,
    val code: String,
    val iso: String,
)