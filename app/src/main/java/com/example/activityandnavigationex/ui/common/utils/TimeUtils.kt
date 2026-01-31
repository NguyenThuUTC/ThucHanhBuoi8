package com.example.activityandnavigationex.ui.common.utils

import android.content.Context
import com.example.activityandnavigationex.R

fun Long.formatTime(context: Context): String {
    val minutes = (this / 60_000) % 60
    val seconds = (this % 60_000) / 1_000
    val centiseconds = (this % 1_000) / 10

    return context.getString(
        R.string.time_format, minutes, seconds, centiseconds
    )
}