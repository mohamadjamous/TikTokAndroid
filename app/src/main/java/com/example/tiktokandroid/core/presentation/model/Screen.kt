package com.example.tiktokandroid.core.presentation.model


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Friends : Screen("friends")
    object Upload : Screen("upload")
    object Notifications : Screen("notifications")
    object Profile : Screen("profile")
    object EmailSignup : Screen("email_signup")
    object Settings : Screen("settings")
    object Display : Screen("display")
    object PhoneNumberSignup : Screen("phone_signup/{phoneNumber}") {
        fun createRoute(phoneNumber: String) = "phone_signup/$phoneNumber"
    }

    object EmailPhoneLogin : Screen("login")

    object Post : Screen("post/{videoUri}") {
        fun createRoute(videoUri: String) = "post/$videoUri"
    }

    object CommentBottomSheet : Screen("comment_bottom_sheet/{videoId}") {
        fun createRoute(videoId: String) = "comment_bottom_sheet/$videoId"
    }

    object AuthBottomSheet : Screen("auth_bottom_sheet")
}







