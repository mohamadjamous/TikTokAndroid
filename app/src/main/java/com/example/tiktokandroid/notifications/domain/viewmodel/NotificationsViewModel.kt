package com.example.tiktokandroid.notifications.domain.viewmodel

import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    fun timeAgoFromNow(timeInMillis: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timeInMillis

        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = days / 30
        val years = days / 365

        return when {
            years > 0 -> "${years}y"
            months > 0 -> "${months}mo"
            weeks > 0 -> "${weeks}w"
            days > 0 -> "${days}d"
            hours > 0 -> "${hours}h"
            minutes > 0 -> "${minutes}m"
            else -> "${seconds}s"
        }
    }

}