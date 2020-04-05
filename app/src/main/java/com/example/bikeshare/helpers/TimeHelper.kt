package com.example.bikeshare.helpers

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
    }
}