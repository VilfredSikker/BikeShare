package com.example.bikeshare.helpers

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimeHelper {
    companion object {
        val pattern = "yyyy-MM-dd HH:mm:ss"
        fun getCurrentTime(): String {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern(pattern)

            return current.format(formatter)
        }

        fun getTimeFromString(time:String) : LocalDate {
            val formatter = DateTimeFormatter.ofPattern(pattern)
            return LocalDate.parse(time, formatter)
        }

        fun getDate(): Date {
            return Calendar.getInstance().time
        }

        @SuppressLint("SimpleDateFormat")
        fun formatDate(date: Date): String {
            val formatter = SimpleDateFormat(pattern)

            return formatter.format(date)
        }
    }
}