package com.devmark.devmark.presentation.utils

import com.devmark.devmark.data.utils.LoggerUtils
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object TimeUtil {
    fun formatCreateAt(createAt: String): String {
        val createTime = parseDateTime(createAt)
        val now = LocalDateTime.now()

        val duration = Duration.between(createTime, now)

        return if (createTime != null) {
            when {
                duration.seconds < 60 -> "방금"
                duration.toMinutes() < 60 -> "${duration.toMinutes()}분 전"
                duration.toHours() < 24 -> "${duration.toHours()}시간 전"
                duration.toDays() < 7 -> "${duration.toDays()}일 전"
                else -> createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            }
        } else {
            "0000-00-00"
        }
    }

    private fun parseDateTime(endTimeString: String): LocalDateTime? {
        val patterns = arrayOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSSSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss.SS",
            "yyyy-MM-dd'T'HH:mm:ss.S",
            "yyyy-MM-dd'T'HH:mm:ss"
        )

        for (pattern in patterns) {
            try {
                return if (pattern.endsWith("Z")) {
                    LocalDateTime.parse(endTimeString, DateTimeFormatter.ofPattern(pattern))
                        .plusHours(9)
                } else {
                    LocalDateTime.parse(
                        endTimeString.replace("Z", ""),
                        DateTimeFormatter.ofPattern(pattern)
                    ).plusHours(9)
                }
            } catch (e: DateTimeParseException) {
                LoggerUtils.error("TimeUtils: $pattern -> 실패")
            }
        }
        return null
    }

}