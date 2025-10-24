package com.example.tiktokandroid.core.presentation.view

sealed class Screen(val rout: String) {
    object Home: Screen("home")
    object Friends: Screen("friends")
    object Upload: Screen("upload")
    object Notifications: Screen("notifications")
    object Profile: Screen("profile")
    object EmailSignup: Screen("email_signup")
}

