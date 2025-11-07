package com.example.tiktokandroid.core.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.tiktokandroid.R

enum class BottomBarDestination(
    val route: String,
    @StringRes val title: Int? = null,
    @DrawableRes val unFilledIcon: Int? = null,
    @DrawableRes val filledIcon: Int? = null,
    @DrawableRes val darkModeIcon: Int? = null
)
{

    // Home
    HOME(
        route = "home",
        title = R.string.home,
        unFilledIcon = R.drawable.ic_home,
        filledIcon = R.drawable.ic_home_fill
    ),

    // Friends
    FRIENDS(
        route = "friends",
        title = R.string.friends,
        unFilledIcon = R.drawable.ic_friends,
        filledIcon = R.drawable.ic_friends
    ),

    // Upload (center button)
    UPLOAD(
        route = "upload",
        unFilledIcon = R.drawable.ic_add_dark,
        darkModeIcon = R.drawable.ic_add_light
    ),

    // Notifications
    NOTIFICATIONS(
        route = "notifications",
        title = R.string.inbox,
        unFilledIcon = R.drawable.ic_inbox,
        filledIcon = R.drawable.ic_inbox_fill
    ),

    // Profile
    PROFILE(
        route = "profile",
        title = R.string.profile,
        unFilledIcon = R.drawable.ic_profile,
        filledIcon = R.drawable.ic_profile_fill
    );

    companion object {
        fun createPhoneSignupRoute(phoneNumber: String) =
            "phone_signup/$phoneNumber"

        fun createPostRoute(videoUri: String) =
            "post/$videoUri"
    }
}