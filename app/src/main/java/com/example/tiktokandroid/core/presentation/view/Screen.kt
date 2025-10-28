package com.example.tiktokandroid.core.presentation.view

sealed class Screen(val rout: String) {
    object Home: Screen("home")
    object Friends: Screen("friends")
    object Upload: Screen("upload")
    object Notifications: Screen("notifications")
    object Profile: Screen("profile")
    object EmailSignup: Screen("email_signup")
    object Settings: Screen("settings")
    object PhoneNumberSignup : Screen("phone_signup/{phoneNumber}") {
        fun createRoute(phoneNumber: String) = "phone_signup/$phoneNumber"
    }
    object EmailPhoneLogin: Screen("login")
    object Post: Screen("post")
}

