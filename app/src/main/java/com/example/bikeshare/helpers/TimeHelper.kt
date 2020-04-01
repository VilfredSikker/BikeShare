package com.example.bikeshare.helpers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimeHelper {
    companion object {
        fun getCurrentTime(): String {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            return current.format(formatter)
        }
    }
}