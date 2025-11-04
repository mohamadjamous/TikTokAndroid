package com.example.tiktokandroid.profile.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.tiktokandroid.R

/**
 * Created by Puskal Khadka on 3/22/2023.
 */

enum class ProfilePagerTabs(
    @StringRes val title: Int? = null,
    @DrawableRes val icon: Int
) {
    PUBLIC_VIDEO(icon = R.drawable.ic_list),
    LIKED_VIDEO(icon = R.drawable.ic_private_like)
}